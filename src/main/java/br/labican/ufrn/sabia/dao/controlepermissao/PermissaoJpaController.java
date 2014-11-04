/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.labican.ufrn.sabia.dao.controlepermissao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.controledepermissao.Perfil;
import br.labican.ufrn.sabia.modelo.controledepermissao.Permissao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class PermissaoJpaController implements Serializable {

    public PermissaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permissao permissao) {
        if (permissao.getPerfils() == null) {
            permissao.setPerfils(new ArrayList<Perfil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(permissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permissao permissao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(permissao);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permissao.getIdPermissao();
                if (findPermissao(id) == null) {
                    throw new NonexistentEntityException("The permissao with id " + id + " no longer exists.");
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
            Permissao permissao;
            try {
                permissao = em.getReference(Permissao.class, id);
                permissao.getIdPermissao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permissao with id " + id + " no longer exists.", enfe);
            }
            em.remove(permissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permissao> findPermissaoEntities() {
        return findPermissaoEntities(true, -1, -1);
    }

    public List<Permissao> findPermissaoEntities(int maxResults, int firstResult) {
        return findPermissaoEntities(false, maxResults, firstResult);
    }

    private List<Permissao> findPermissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permissao.class));
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

    public Permissao findPermissao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permissao> rt = cq.from(Permissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
