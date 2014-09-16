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
import br.labican.ufrn.sabia.modelo.cadastroibge.Mesorregiao;
import br.labican.ufrn.sabia.modelo.cadastroibge.Cidade;
import br.labican.ufrn.sabia.modelo.cadastroibge.Microrregiao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class MicrorregiaoJpaController implements Serializable {

    public MicrorregiaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Microrregiao microrregiao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(microrregiao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Microrregiao microrregiao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(microrregiao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = microrregiao.getIdMicrorregiao();
                if (findMicrorregiao(id) == null) {
                    throw new NonexistentEntityException("The microrregiao with id " + id + " no longer exists.");
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
            Microrregiao microrregiao;
            try {
                microrregiao = em.getReference(Microrregiao.class, id);
                microrregiao.getIdMicrorregiao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The microrregiao with id " + id + " no longer exists.", enfe);
            }
            em.remove(microrregiao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Microrregiao> findMicrorregiaoEntities() {
        return findMicrorregiaoEntities(true, -1, -1);
    }

    public List<Microrregiao> findMicrorregiaoEntities(int maxResults, int firstResult) {
        return findMicrorregiaoEntities(false, maxResults, firstResult);
    }

    private List<Microrregiao> findMicrorregiaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Microrregiao.class));
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

    public Microrregiao findMicrorregiao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Microrregiao.class, id);
        } finally {
            em.close();
        }
    }

    public int getMicrorregiaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Microrregiao> rt = cq.from(Microrregiao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
