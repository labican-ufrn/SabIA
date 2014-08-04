
package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Autorizacao;
import br.labican.ufrn.sabia.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge Maia
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getAutorizacoes() == null) {
            usuario.setAutorizacoes(new ArrayList<Autorizacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Autorizacao> attachedAutorizacoes = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacoesAutorizacaoToAttach : usuario.getAutorizacoes()) {
                autorizacoesAutorizacaoToAttach = em.getReference(autorizacoesAutorizacaoToAttach.getClass(), autorizacoesAutorizacaoToAttach.getId());
                attachedAutorizacoes.add(autorizacoesAutorizacaoToAttach);
            }
            usuario.setAutorizacoes(attachedAutorizacoes);
            em.persist(usuario);
            for (Autorizacao autorizacoesAutorizacao : usuario.getAutorizacoes()) {
                autorizacoesAutorizacao.getUsuarios().add(usuario);
                autorizacoesAutorizacao = em.merge(autorizacoesAutorizacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<Autorizacao> autorizacoesOld = persistentUsuario.getAutorizacoes();
            List<Autorizacao> autorizacoesNew = usuario.getAutorizacoes();
            List<Autorizacao> attachedAutorizacoesNew = new ArrayList<Autorizacao>();
            for (Autorizacao autorizacoesNewAutorizacaoToAttach : autorizacoesNew) {
                autorizacoesNewAutorizacaoToAttach = em.getReference(autorizacoesNewAutorizacaoToAttach.getClass(), autorizacoesNewAutorizacaoToAttach.getId());
                attachedAutorizacoesNew.add(autorizacoesNewAutorizacaoToAttach);
            }
            autorizacoesNew = attachedAutorizacoesNew;
            usuario.setAutorizacoes(autorizacoesNew);
            usuario = em.merge(usuario);
            for (Autorizacao autorizacoesOldAutorizacao : autorizacoesOld) {
                if (!autorizacoesNew.contains(autorizacoesOldAutorizacao)) {
                    autorizacoesOldAutorizacao.getUsuarios().remove(usuario);
                    autorizacoesOldAutorizacao = em.merge(autorizacoesOldAutorizacao);
                }
            }
            for (Autorizacao autorizacoesNewAutorizacao : autorizacoesNew) {
                if (!autorizacoesOld.contains(autorizacoesNewAutorizacao)) {
                    autorizacoesNewAutorizacao.getUsuarios().add(usuario);
                    autorizacoesNewAutorizacao = em.merge(autorizacoesNewAutorizacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Autorizacao> autorizacoes = usuario.getAutorizacoes();
            for (Autorizacao autorizacoesAutorizacao : autorizacoes) {
                autorizacoesAutorizacao.getUsuarios().remove(usuario);
                autorizacoesAutorizacao = em.merge(autorizacoesAutorizacao);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
