package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.Avaliador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.EixoTecnologico;
import java.util.ArrayList;
import java.util.List;
import br.labican.ufrn.sabia.modelo.ConfirmacaoAvaliador;
import br.labican.ufrn.sabia.modelo.Equipe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class AvaliadorJpaController implements Serializable {

    public AvaliadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliador avaliador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(avaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliador avaliador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(avaliador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avaliador.getIdAvaliador();
                if (findAvaliador(id) == null) {
                    throw new NonexistentEntityException("The avaliador with id " + id + " no longer exists.");
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
            Avaliador avaliador;
            try {
                avaliador = em.getReference(Avaliador.class, id);
                avaliador.getIdAvaliador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliador with id " + id + " no longer exists.", enfe);
            }
            em.remove(avaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliador> findAvaliadorEntities() {
        return findAvaliadorEntities(true, -1, -1);
    }

    public List<Avaliador> findAvaliadorEntities(int maxResults, int firstResult) {
        return findAvaliadorEntities(false, maxResults, firstResult);
    }

    private List<Avaliador> findAvaliadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliador.class));
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

    public Avaliador findAvaliador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliador> rt = cq.from(Avaliador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
