package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Instituicao;
import br.labican.ufrn.sabia.modelo.SistemaEnsino;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class SistemaEnsinoJpaController implements Serializable {

    public SistemaEnsinoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SistemaEnsino sistemaEnsino) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sistemaEnsino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SistemaEnsino sistemaEnsino) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(sistemaEnsino);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sistemaEnsino.getIdSistemaEnsino();
                if (findSistemaEnsino(id) == null) {
                    throw new NonexistentEntityException("The sistemaEnsino with id " + id + " no longer exists.");
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
            SistemaEnsino sistemaEnsino;
            try {
                sistemaEnsino = em.getReference(SistemaEnsino.class, id);
                sistemaEnsino.getIdSistemaEnsino();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sistemaEnsino with id " + id + " no longer exists.", enfe);
            }
            em.remove(sistemaEnsino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SistemaEnsino> findSistemaEnsinoEntities() {
        return findSistemaEnsinoEntities(true, -1, -1);
    }

    public List<SistemaEnsino> findSistemaEnsinoEntities(int maxResults, int firstResult) {
        return findSistemaEnsinoEntities(false, maxResults, firstResult);
    }

    private List<SistemaEnsino> findSistemaEnsinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SistemaEnsino.class));
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

    public SistemaEnsino findSistemaEnsino(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SistemaEnsino.class, id);
        } finally {
            em.close();
        }
    }

    public int getSistemaEnsinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SistemaEnsino> rt = cq.from(SistemaEnsino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
