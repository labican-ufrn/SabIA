package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Atividade;
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
public class AtividadeJpaController implements Serializable {

    public AtividadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividade atividade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(atividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Atividade atividade) throws NonexistentEntityException,
            Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(atividade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = atividade.getIdAtividade();
                if (findAtividade(id) == null) {
                    throw new NonexistentEntityException(
                            "The atividade with id " + id
                                    + " no longer exists.");
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
            Atividade atividade;
            try {
                atividade = em.getReference(Atividade.class, id);
                atividade.getIdAtividade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividade with id "
                        + id + " no longer exists.", enfe);
            }
            em.remove(atividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Atividade> findAtividadeEntities() {
        return findAtividadeEntities(true, -1, -1);
    }

    public List<Atividade> findAtividadeEntities(int maxResults, int firstResult) {
        return findAtividadeEntities(false, maxResults, firstResult);
    }

    private List<Atividade> findAtividadeEntities(boolean all, int maxResults,
            int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividade.class));
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

    public Atividade findAtividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividade> rt = cq.from(Atividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
