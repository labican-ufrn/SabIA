package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.StatusAvaliador;
import br.labican.ufrn.sabia.modelo.TipoStatusAvaliador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class TipoStatusAvaliadorJpaController implements Serializable {

    public TipoStatusAvaliadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoStatusAvaliador tipoStatusAvaliador) {
        if (tipoStatusAvaliador.getStatusAvaliadors() == null) {
            tipoStatusAvaliador.setStatusAvaliadors(new ArrayList<StatusAvaliador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoStatusAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoStatusAvaliador tipoStatusAvaliador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(tipoStatusAvaliador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoStatusAvaliador.getIdTipoStatusAvaliador();
                if (findTipoStatusAvaliador(id) == null) {
                    throw new NonexistentEntityException("The tipoStatusAvaliador with id " + id + " no longer exists.");
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
            TipoStatusAvaliador tipoStatusAvaliador;
            try {
                tipoStatusAvaliador = em.getReference(TipoStatusAvaliador.class, id);
                tipoStatusAvaliador.getIdTipoStatusAvaliador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoStatusAvaliador with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoStatusAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoStatusAvaliador> findTipoStatusAvaliadorEntities() {
        return findTipoStatusAvaliadorEntities(true, -1, -1);
    }

    public List<TipoStatusAvaliador> findTipoStatusAvaliadorEntities(int maxResults, int firstResult) {
        return findTipoStatusAvaliadorEntities(false, maxResults, firstResult);
    }

    private List<TipoStatusAvaliador> findTipoStatusAvaliadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoStatusAvaliador.class));
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

    public TipoStatusAvaliador findTipoStatusAvaliador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoStatusAvaliador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoStatusAvaliadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoStatusAvaliador> rt = cq.from(TipoStatusAvaliador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
