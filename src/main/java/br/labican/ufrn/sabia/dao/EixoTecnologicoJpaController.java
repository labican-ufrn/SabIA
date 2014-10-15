package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Avaliador;
import br.labican.ufrn.sabia.modelo.EixoTecnologico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class EixoTecnologicoJpaController implements Serializable {

    public EixoTecnologicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EixoTecnologico eixoTecnologico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(eixoTecnologico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EixoTecnologico eixoTecnologico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(eixoTecnologico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eixoTecnologico.getIdEixoTecnologico();
                if (findEixoTecnologico(id) == null) {
                    throw new NonexistentEntityException("The eixoTecnologico with id " + id + " no longer exists.");
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
            EixoTecnologico eixoTecnologico;
            try {
                eixoTecnologico = em.getReference(EixoTecnologico.class, id);
                eixoTecnologico.getIdEixoTecnologico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eixoTecnologico with id " + id + " no longer exists.", enfe);
            }
            em.remove(eixoTecnologico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EixoTecnologico> findEixoTecnologicoEntities() {
        return findEixoTecnologicoEntities(true, -1, -1);
    }

    public List<EixoTecnologico> findEixoTecnologicoEntities(int maxResults, int firstResult) {
        return findEixoTecnologicoEntities(false, maxResults, firstResult);
    }

    private List<EixoTecnologico> findEixoTecnologicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EixoTecnologico.class));
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

    public EixoTecnologico findEixoTecnologico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EixoTecnologico.class, id);
        } finally {
            em.close();
        }
    }

    public int getEixoTecnologicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EixoTecnologico> rt = cq.from(EixoTecnologico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
