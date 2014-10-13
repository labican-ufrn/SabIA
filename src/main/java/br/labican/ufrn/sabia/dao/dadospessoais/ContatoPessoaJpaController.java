/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.labican.ufrn.sabia.dao.dadospessoais;

import br.labican.ufrn.sabia.dao.dadospessoais.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.dadospessoais.ContatoPessoa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.dadospessoais.Pessoa;
import br.labican.ufrn.sabia.modelo.dadospessoais.TipoContato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class ContatoPessoaJpaController implements Serializable {

    public ContatoPessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContatoPessoa contatoPessoa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contatoPessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContatoPessoa contatoPessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contatoPessoa = em.merge(contatoPessoa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contatoPessoa.getIdContatoPessoa();
                if (findContatoPessoa(id) == null) {
                    throw new NonexistentEntityException("The contatoPessoa with id " + id + " no longer exists.");
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
            ContatoPessoa contatoPessoa;
            try {
                contatoPessoa = em.getReference(ContatoPessoa.class, id);
                contatoPessoa.getIdContatoPessoa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contatoPessoa with id " + id + " no longer exists.", enfe);
            }
            em.remove(contatoPessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContatoPessoa> findContatoPessoaEntities() {
        return findContatoPessoaEntities(true, -1, -1);
    }

    public List<ContatoPessoa> findContatoPessoaEntities(int maxResults, int firstResult) {
        return findContatoPessoaEntities(false, maxResults, firstResult);
    }

    private List<ContatoPessoa> findContatoPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContatoPessoa.class));
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

    public ContatoPessoa findContatoPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContatoPessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContatoPessoa> rt = cq.from(ContatoPessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
