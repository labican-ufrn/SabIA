/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.Avaliador;
import br.labican.ufrn.sabia.modelo.StatusAvaliador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class StatusAvaliadorJpaController implements Serializable {

    public StatusAvaliadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StatusAvaliador statusAvaliador) {
        if (statusAvaliador.getAvaliadors() == null) {
            statusAvaliador.setAvaliadors(new ArrayList<Avaliador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(statusAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StatusAvaliador statusAvaliador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(statusAvaliador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = statusAvaliador.getIdStatusAvaliador();
                if (findStatusAvaliador(id) == null) {
                    throw new NonexistentEntityException("The statusAvaliador with id " + id + " no longer exists.");
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
            StatusAvaliador statusAvaliador;
            try {
                statusAvaliador = em.getReference(StatusAvaliador.class, id);
                statusAvaliador.getIdStatusAvaliador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The statusAvaliador with id " + id + " no longer exists.", enfe);
            }
            em.remove(statusAvaliador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StatusAvaliador> findStatusAvaliadorEntities() {
        return findStatusAvaliadorEntities(true, -1, -1);
    }

    public List<StatusAvaliador> findStatusAvaliadorEntities(int maxResults, int firstResult) {
        return findStatusAvaliadorEntities(false, maxResults, firstResult);
    }

    private List<StatusAvaliador> findStatusAvaliadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StatusAvaliador.class));
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

    public StatusAvaliador findStatusAvaliador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StatusAvaliador.class, id);
        } finally {
            em.close();
        }
    }

    public int getStatusAvaliadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StatusAvaliador> rt = cq.from(StatusAvaliador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
