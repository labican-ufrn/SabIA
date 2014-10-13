/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.labican.ufrn.sabia.dao.dadospessoais;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.labican.ufrn.sabia.dao.dadospessoais.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.dadospessoais.ContatoPessoa;
import br.labican.ufrn.sabia.modelo.dadospessoais.TipoContato;

/**
 *
 * @author Rummenigge
 */
public class TipoContatoJpaController implements Serializable {

    public TipoContatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContato tipoContato) {
        if (tipoContato.getContatoPessoas() == null) {
            tipoContato.setContatoPessoas(new ArrayList<ContatoPessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoContato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContato tipoContato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoContato = em.merge(tipoContato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContato.getIdTipoContato();
                if (findTipoContato(id) == null) {
                    throw new NonexistentEntityException("The tipoContato with id " + id + " no longer exists.");
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
            TipoContato tipoContato;
            try {
                tipoContato = em.getReference(TipoContato.class, id);
                tipoContato.getIdTipoContato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContato with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoContato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContato> findTipoContatoEntities() {
        return findTipoContatoEntities(true, -1, -1);
    }

    public List<TipoContato> findTipoContatoEntities(int maxResults, int firstResult) {
        return findTipoContatoEntities(false, maxResults, firstResult);
    }

    private List<TipoContato> findTipoContatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContato.class));
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

    public TipoContato findTipoContato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContato.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContato> rt = cq.from(TipoContato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
