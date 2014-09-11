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
import br.labican.ufrn.sabia.modelo.cadastroibge.Macrorregiao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class MacrorregiaoJpaController implements Serializable {

    public MacrorregiaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Macrorregiao macrorregiao) {
        if (macrorregiao.getEstados() == null) {
            macrorregiao.setEstados(new ArrayList<Estado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estado> attachedEstados = new ArrayList<Estado>();
            for (Estado estadosEstadoToAttach : macrorregiao.getEstados()) {
                estadosEstadoToAttach = em.getReference(estadosEstadoToAttach.getClass(), estadosEstadoToAttach.getIdEstado());
                attachedEstados.add(estadosEstadoToAttach);
            }
            macrorregiao.setEstados(attachedEstados);
            em.persist(macrorregiao);
            for (Estado estadosEstado : macrorregiao.getEstados()) {
                Macrorregiao oldMacrorregiaoOfEstadosEstado = estadosEstado.getMacrorregiao();
                estadosEstado.setMacrorregiao(macrorregiao);
                estadosEstado = em.merge(estadosEstado);
                if (oldMacrorregiaoOfEstadosEstado != null) {
                    oldMacrorregiaoOfEstadosEstado.getEstados().remove(estadosEstado);
                    oldMacrorregiaoOfEstadosEstado = em.merge(oldMacrorregiaoOfEstadosEstado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Macrorregiao macrorregiao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macrorregiao persistentMacrorregiao = em.find(Macrorregiao.class, macrorregiao.getIdMacrorregiao());
            List<Estado> estadosOld = persistentMacrorregiao.getEstados();
            List<Estado> estadosNew = macrorregiao.getEstados();
            List<Estado> attachedEstadosNew = new ArrayList<Estado>();
            for (Estado estadosNewEstadoToAttach : estadosNew) {
                estadosNewEstadoToAttach = em.getReference(estadosNewEstadoToAttach.getClass(), estadosNewEstadoToAttach.getIdEstado());
                attachedEstadosNew.add(estadosNewEstadoToAttach);
            }
            estadosNew = attachedEstadosNew;
            macrorregiao.setEstados(estadosNew);
            macrorregiao = em.merge(macrorregiao);
            for (Estado estadosOldEstado : estadosOld) {
                if (!estadosNew.contains(estadosOldEstado)) {
                    estadosOldEstado.setMacrorregiao(null);
                    estadosOldEstado = em.merge(estadosOldEstado);
                }
            }
            for (Estado estadosNewEstado : estadosNew) {
                if (!estadosOld.contains(estadosNewEstado)) {
                    Macrorregiao oldMacrorregiaoOfEstadosNewEstado = estadosNewEstado.getMacrorregiao();
                    estadosNewEstado.setMacrorregiao(macrorregiao);
                    estadosNewEstado = em.merge(estadosNewEstado);
                    if (oldMacrorregiaoOfEstadosNewEstado != null && !oldMacrorregiaoOfEstadosNewEstado.equals(macrorregiao)) {
                        oldMacrorregiaoOfEstadosNewEstado.getEstados().remove(estadosNewEstado);
                        oldMacrorregiaoOfEstadosNewEstado = em.merge(oldMacrorregiaoOfEstadosNewEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = macrorregiao.getIdMacrorregiao();
                if (findMacrorregiao(id) == null) {
                    throw new NonexistentEntityException("The macrorregiao with id " + id + " no longer exists.");
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
            Macrorregiao macrorregiao;
            try {
                macrorregiao = em.getReference(Macrorregiao.class, id);
                macrorregiao.getIdMacrorregiao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The macrorregiao with id " + id + " no longer exists.", enfe);
            }
            List<Estado> estados = macrorregiao.getEstados();
            for (Estado estadosEstado : estados) {
                estadosEstado.setMacrorregiao(null);
                estadosEstado = em.merge(estadosEstado);
            }
            em.remove(macrorregiao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Macrorregiao> findMacrorregiaoEntities() {
        return findMacrorregiaoEntities(true, -1, -1);
    }

    public List<Macrorregiao> findMacrorregiaoEntities(int maxResults, int firstResult) {
        return findMacrorregiaoEntities(false, maxResults, firstResult);
    }

    private List<Macrorregiao> findMacrorregiaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Macrorregiao.class));
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

    public Macrorregiao findMacrorregiao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Macrorregiao.class, id);
        } finally {
            em.close();
        }
    }

    public int getMacrorregiaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Macrorregiao> rt = cq.from(Macrorregiao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
