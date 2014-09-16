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
import br.labican.ufrn.sabia.modelo.cadastroibge.Estado;
import br.labican.ufrn.sabia.modelo.cadastroibge.Mesorregiao;
import br.labican.ufrn.sabia.modelo.cadastroibge.Microrregiao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class MesorregiaoJpaController implements Serializable {

	public MesorregiaoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Mesorregiao mesorregiao) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(mesorregiao);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Mesorregiao mesorregiao)
			throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.merge(mesorregiao);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = mesorregiao.getIdMesorregiao();
				if (findMesorregiao(id) == null) {
					throw new NonexistentEntityException(
							"The mesorregiao with id " + id
									+ " no longer exists.");
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
			Mesorregiao mesorregiao;
			try {
				mesorregiao = em.getReference(Mesorregiao.class, id);
				mesorregiao.getIdMesorregiao();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The mesorregiao with id "
						+ id + " no longer exists.", enfe);
			}
			em.remove(mesorregiao);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Mesorregiao> findMesorregiaoEntities() {
		return findMesorregiaoEntities(true, -1, -1);
	}

	public List<Mesorregiao> findMesorregiaoEntities(int maxResults,
			int firstResult) {
		return findMesorregiaoEntities(false, maxResults, firstResult);
	}

	private List<Mesorregiao> findMesorregiaoEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Mesorregiao.class));
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

	public Mesorregiao findMesorregiao(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Mesorregiao.class, id);
		} finally {
			em.close();
		}
	}

	public int getMesorregiaoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Mesorregiao> rt = cq.from(Mesorregiao.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
