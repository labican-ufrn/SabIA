package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Autorizacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class AutorizacaoJpaController implements Serializable {

    public AutorizacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autorizacao autorizacao) {
        if (autorizacao.getUsuarios() == null) {
            autorizacao.setUsuarios(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarios = new ArrayList<Usuario>();
            for (Usuario usuariosUsuarioToAttach : autorizacao.getUsuarios()) {
                usuariosUsuarioToAttach = em.getReference(usuariosUsuarioToAttach.getClass(), usuariosUsuarioToAttach.getId());
                attachedUsuarios.add(usuariosUsuarioToAttach);
            }
            autorizacao.setUsuarios(attachedUsuarios);
            em.persist(autorizacao);
            for (Usuario usuariosUsuario : autorizacao.getUsuarios()) {
                usuariosUsuario.getAutorizacoes().add(autorizacao);
                usuariosUsuario = em.merge(usuariosUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autorizacao autorizacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autorizacao persistentAutorizacao = em.find(Autorizacao.class, autorizacao.getId());
            List<Usuario> usuariosOld = persistentAutorizacao.getUsuarios();
            List<Usuario> usuariosNew = autorizacao.getUsuarios();
            List<Usuario> attachedUsuariosNew = new ArrayList<Usuario>();
            for (Usuario usuariosNewUsuarioToAttach : usuariosNew) {
                usuariosNewUsuarioToAttach = em.getReference(usuariosNewUsuarioToAttach.getClass(), usuariosNewUsuarioToAttach.getId());
                attachedUsuariosNew.add(usuariosNewUsuarioToAttach);
            }
            usuariosNew = attachedUsuariosNew;
            autorizacao.setUsuarios(usuariosNew);
            autorizacao = em.merge(autorizacao);
            for (Usuario usuariosOldUsuario : usuariosOld) {
                if (!usuariosNew.contains(usuariosOldUsuario)) {
                    usuariosOldUsuario.getAutorizacoes().remove(autorizacao);
                    usuariosOldUsuario = em.merge(usuariosOldUsuario);
                }
            }
            for (Usuario usuariosNewUsuario : usuariosNew) {
                if (!usuariosOld.contains(usuariosNewUsuario)) {
                    usuariosNewUsuario.getAutorizacoes().add(autorizacao);
                    usuariosNewUsuario = em.merge(usuariosNewUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = autorizacao.getId();
                if (findAutorizacao(id) == null) {
                    throw new NonexistentEntityException("The autorizacao with id " + id + " no longer exists.");
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
            Autorizacao autorizacao;
            try {
                autorizacao = em.getReference(Autorizacao.class, id);
                autorizacao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autorizacao with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> usuarios = autorizacao.getUsuarios();
            for (Usuario usuariosUsuario : usuarios) {
                usuariosUsuario.getAutorizacoes().remove(autorizacao);
                usuariosUsuario = em.merge(usuariosUsuario);
            }
            em.remove(autorizacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autorizacao> findAutorizacaoEntities() {
        return findAutorizacaoEntities(true, -1, -1);
    }

    public List<Autorizacao> findAutorizacaoEntities(int maxResults, int firstResult) {
        return findAutorizacaoEntities(false, maxResults, firstResult);
    }

    private List<Autorizacao> findAutorizacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autorizacao.class));
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

    public Autorizacao findAutorizacao(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autorizacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutorizacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autorizacao> rt = cq.from(Autorizacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
