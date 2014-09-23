package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Titulacao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Rummenigge
 */
public class TitulacaoJpaController implements Serializable {

    public TitulacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Titulacao titulacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(titulacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Titulacao titulacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            titulacao = em.merge(titulacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = titulacao.getIdTitulacao();
                if (findTitulacao(id) == null) {
                    throw new NonexistentEntityException("The titulacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Titulacao titulacao;
            try {
                titulacao = em.getReference(Titulacao.class, id);
                titulacao.getIdTitulacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The titulacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(titulacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Titulacao> findTitulacaoEntities() {
        return findTitulacaoEntities(true, -1, -1);
    }

    public List<Titulacao> findTitulacaoEntities(int maxResults, int firstResult) {
        return findTitulacaoEntities(false, maxResults, firstResult);
    }

    private List<Titulacao> findTitulacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Titulacao.class));
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

    public Titulacao findTitulacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Titulacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTitulacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Titulacao> rt = cq.from(Titulacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
