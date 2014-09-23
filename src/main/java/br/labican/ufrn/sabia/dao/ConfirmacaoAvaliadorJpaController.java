package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.ConfirmacaoAvaliador;
import br.labican.ufrn.sabia.modelo.Viagem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class ConfirmacaoAvaliadorJpaController implements Serializable {

    public ConfirmacaoAvaliadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfirmacaoAvaliador confirmacaoAvaliador) {
        if (confirmacaoAvaliador.getViagems() == null) {
            confirmacaoAvaliador.setViagems(new ArrayList<Viagem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(confirmacaoAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfirmacaoAvaliador confirmacaoAvaliador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(confirmacaoAvaliador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = confirmacaoAvaliador.getIdConfirmacaoAvaliador();
                if (findConfirmacaoAvaliador(id) == null) {
                    throw new NonexistentEntityException("The confirmacaoAvaliador with id " + id + " no longer exists.");
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
            ConfirmacaoAvaliador confirmacaoAvaliador;
            try {
                confirmacaoAvaliador = em.getReference(ConfirmacaoAvaliador.class, id);
                confirmacaoAvaliador.getIdConfirmacaoAvaliador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The confirmacaoAvaliador with id " + id + " no longer exists.", enfe);
            }
            em.remove(confirmacaoAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConfirmacaoAvaliador> findConfirmacaoAvaliadorEntities() {
        return findConfirmacaoAvaliadorEntities(true, -1, -1);
    }

    public List<ConfirmacaoAvaliador> findConfirmacaoAvaliadorEntities(int maxResults, int firstResult) {
        return findConfirmacaoAvaliadorEntities(false, maxResults, firstResult);
    }

    private List<ConfirmacaoAvaliador> findConfirmacaoAvaliadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfirmacaoAvaliador.class));
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

    public ConfirmacaoAvaliador findConfirmacaoAvaliador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfirmacaoAvaliador.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfirmacaoAvaliadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfirmacaoAvaliador> rt = cq.from(ConfirmacaoAvaliador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
