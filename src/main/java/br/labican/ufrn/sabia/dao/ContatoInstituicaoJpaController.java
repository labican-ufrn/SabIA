/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.ContatoInstituicao;
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
public class ContatoInstituicaoJpaController implements Serializable {

    public ContatoInstituicaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContatoInstituicao contatoInstituicao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contatoInstituicao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContatoInstituicao contatoInstituicao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(contatoInstituicao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contatoInstituicao.getIdContatoInstituicao();
                if (findContatoInstituicao(id) == null) {
                    throw new NonexistentEntityException("The contatoInstituicao with id " + id + " no longer exists.");
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
            ContatoInstituicao contatoInstituicao;
            try {
                contatoInstituicao = em.getReference(ContatoInstituicao.class, id);
                contatoInstituicao.getIdContatoInstituicao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contatoInstituicao with id " + id + " no longer exists.", enfe);
            }
            em.remove(contatoInstituicao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContatoInstituicao> findContatoInstituicaoEntities() {
        return findContatoInstituicaoEntities(true, -1, -1);
    }

    public List<ContatoInstituicao> findContatoInstituicaoEntities(int maxResults, int firstResult) {
        return findContatoInstituicaoEntities(false, maxResults, firstResult);
    }

    private List<ContatoInstituicao> findContatoInstituicaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContatoInstituicao.class));
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

    public ContatoInstituicao findContatoInstituicao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContatoInstituicao.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoInstituicaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContatoInstituicao> rt = cq.from(ContatoInstituicao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
