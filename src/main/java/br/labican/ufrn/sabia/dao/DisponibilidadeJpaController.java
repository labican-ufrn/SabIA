package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Disponibilidade;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class DisponibilidadeJpaController implements Serializable {

    public DisponibilidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disponibilidade disponibilidade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(disponibilidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disponibilidade disponibilidade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(disponibilidade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = disponibilidade.getIdDisponibilidade();
                if (findDisponibilidade(id) == null) {
                    throw new NonexistentEntityException("The disponibilidade with id " + id + " no longer exists.");
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
            Disponibilidade disponibilidade;
            try {
                disponibilidade = em.getReference(Disponibilidade.class, id);
                disponibilidade.getIdDisponibilidade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disponibilidade with id " + id + " no longer exists.", enfe);
            }
            em.remove(disponibilidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disponibilidade> findDisponibilidadeEntities() {
        return findDisponibilidadeEntities(true, -1, -1);
    }

    public List<Disponibilidade> findDisponibilidadeEntities(int maxResults, int firstResult) {
        return findDisponibilidadeEntities(false, maxResults, firstResult);
    }

    private List<Disponibilidade> findDisponibilidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disponibilidade.class));
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

    public Disponibilidade findDisponibilidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disponibilidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisponibilidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disponibilidade> rt = cq.from(Disponibilidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
