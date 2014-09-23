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
import br.labican.ufrn.sabia.modelo.Avaliacao;
import java.util.ArrayList;
import java.util.List;
import br.labican.ufrn.sabia.modelo.Avaliador;
import br.labican.ufrn.sabia.modelo.Equipe;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class EquipeJpaController implements Serializable {

    public EquipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipe equipe) {
        if (equipe.getAvaliacaos() == null) {
            equipe.setAvaliacaos(new ArrayList<Avaliacao>());
        }
        if (equipe.getAvaliadors() == null) {
            equipe.setAvaliadors(new ArrayList<Avaliador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipe equipe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(equipe);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipe.getIdEquipe();
                if (findEquipe(id) == null) {
                    throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.");
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
            Equipe equipe;
            try {
                equipe = em.getReference(Equipe.class, id);
                equipe.getIdEquipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipe with id " + id + " no longer exists.", enfe);
            }
            em.remove(equipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipe> findEquipeEntities() {
        return findEquipeEntities(true, -1, -1);
    }

    public List<Equipe> findEquipeEntities(int maxResults, int firstResult) {
        return findEquipeEntities(false, maxResults, firstResult);
    }

    private List<Equipe> findEquipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipe.class));
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

    public Equipe findEquipe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipe> rt = cq.from(Equipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
