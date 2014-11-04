package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Avaliacao;
import java.util.ArrayList;
import java.util.List;
import br.labican.ufrn.sabia.modelo.Disponibilidade;
import br.labican.ufrn.sabia.modelo.SemanaAvaliacao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class SemanaAvaliacaoJpaController implements Serializable {

    public SemanaAvaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SemanaAvaliacao semanaAvaliacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(semanaAvaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SemanaAvaliacao semanaAvaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(semanaAvaliacao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = semanaAvaliacao.getIdSemanaAvaliacao();
                if (findSemanaAvaliacao(id) == null) {
                    throw new NonexistentEntityException("The semanaAvaliacao with id " + id + " no longer exists.");
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
            SemanaAvaliacao semanaAvaliacao;
            try {
                semanaAvaliacao = em.getReference(SemanaAvaliacao.class, id);
                semanaAvaliacao.getIdSemanaAvaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semanaAvaliacao with id " + id + " no longer exists.", enfe);
            }
            em.remove(semanaAvaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SemanaAvaliacao> findSemanaAvaliacaoEntities() {
        return findSemanaAvaliacaoEntities(true, -1, -1);
    }

    public List<SemanaAvaliacao> findSemanaAvaliacaoEntities(int maxResults, int firstResult) {
        return findSemanaAvaliacaoEntities(false, maxResults, firstResult);
    }

    private List<SemanaAvaliacao> findSemanaAvaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SemanaAvaliacao.class));
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

    public SemanaAvaliacao findSemanaAvaliacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SemanaAvaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getSemanaAvaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SemanaAvaliacao> rt = cq.from(SemanaAvaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
