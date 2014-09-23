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
import br.labican.ufrn.sabia.modelo.Instituicao;
import br.labican.ufrn.sabia.modelo.TipoInstituicao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class TipoInstituicaoJpaController implements Serializable {

    public TipoInstituicaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoInstituicao tipoInstituicao) {
        if (tipoInstituicao.getInstituicaos() == null) {
            tipoInstituicao.setInstituicaos(new ArrayList<Instituicao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoInstituicao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoInstituicao tipoInstituicao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(tipoInstituicao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoInstituicao.getIdTipoInstituicao();
                if (findTipoInstituicao(id) == null) {
                    throw new NonexistentEntityException("The tipoInstituicao with id " + id + " no longer exists.");
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
            TipoInstituicao tipoInstituicao;
            try {
                tipoInstituicao = em.getReference(TipoInstituicao.class, id);
                tipoInstituicao.getIdTipoInstituicao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoInstituicao with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoInstituicao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoInstituicao> findTipoInstituicaoEntities() {
        return findTipoInstituicaoEntities(true, -1, -1);
    }

    public List<TipoInstituicao> findTipoInstituicaoEntities(int maxResults, int firstResult) {
        return findTipoInstituicaoEntities(false, maxResults, firstResult);
    }

    private List<TipoInstituicao> findTipoInstituicaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoInstituicao.class));
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

    public TipoInstituicao findTipoInstituicao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoInstituicao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoInstituicaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoInstituicao> rt = cq.from(TipoInstituicao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
