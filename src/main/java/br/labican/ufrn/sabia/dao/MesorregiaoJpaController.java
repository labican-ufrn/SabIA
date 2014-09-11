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
        if (mesorregiao.getMicrorregiaos() == null) {
            mesorregiao.setMicrorregiaos(new ArrayList<Microrregiao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estado = mesorregiao.getEstado();
            if (estado != null) {
                estado = em.getReference(estado.getClass(), estado.getIdEstado());
                mesorregiao.setEstado(estado);
            }
            List<Microrregiao> attachedMicrorregiaos = new ArrayList<Microrregiao>();
            for (Microrregiao microrregiaosMicrorregiaoToAttach : mesorregiao.getMicrorregiaos()) {
                microrregiaosMicrorregiaoToAttach = em.getReference(microrregiaosMicrorregiaoToAttach.getClass(), microrregiaosMicrorregiaoToAttach.getIdMicrorregiao());
                attachedMicrorregiaos.add(microrregiaosMicrorregiaoToAttach);
            }
            mesorregiao.setMicrorregiaos(attachedMicrorregiaos);
            em.persist(mesorregiao);
            if (estado != null) {
                estado.getMesorregiaos().add(mesorregiao);
                estado = em.merge(estado);
            }
            for (Microrregiao microrregiaosMicrorregiao : mesorregiao.getMicrorregiaos()) {
                Mesorregiao oldMesorregiaoOfMicrorregiaosMicrorregiao = microrregiaosMicrorregiao.getMesorregiao();
                microrregiaosMicrorregiao.setMesorregiao(mesorregiao);
                microrregiaosMicrorregiao = em.merge(microrregiaosMicrorregiao);
                if (oldMesorregiaoOfMicrorregiaosMicrorregiao != null) {
                    oldMesorregiaoOfMicrorregiaosMicrorregiao.getMicrorregiaos().remove(microrregiaosMicrorregiao);
                    oldMesorregiaoOfMicrorregiaosMicrorregiao = em.merge(oldMesorregiaoOfMicrorregiaosMicrorregiao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mesorregiao mesorregiao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mesorregiao persistentMesorregiao = em.find(Mesorregiao.class, mesorregiao.getIdMesorregiao());
            Estado estadoOld = persistentMesorregiao.getEstado();
            Estado estadoNew = mesorregiao.getEstado();
            List<Microrregiao> microrregiaosOld = persistentMesorregiao.getMicrorregiaos();
            List<Microrregiao> microrregiaosNew = mesorregiao.getMicrorregiaos();
            if (estadoNew != null) {
                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getIdEstado());
                mesorregiao.setEstado(estadoNew);
            }
            List<Microrregiao> attachedMicrorregiaosNew = new ArrayList<Microrregiao>();
            for (Microrregiao microrregiaosNewMicrorregiaoToAttach : microrregiaosNew) {
                microrregiaosNewMicrorregiaoToAttach = em.getReference(microrregiaosNewMicrorregiaoToAttach.getClass(), microrregiaosNewMicrorregiaoToAttach.getIdMicrorregiao());
                attachedMicrorregiaosNew.add(microrregiaosNewMicrorregiaoToAttach);
            }
            microrregiaosNew = attachedMicrorregiaosNew;
            mesorregiao.setMicrorregiaos(microrregiaosNew);
            mesorregiao = em.merge(mesorregiao);
            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
                estadoOld.getMesorregiaos().remove(mesorregiao);
                estadoOld = em.merge(estadoOld);
            }
            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
                estadoNew.getMesorregiaos().add(mesorregiao);
                estadoNew = em.merge(estadoNew);
            }
            for (Microrregiao microrregiaosOldMicrorregiao : microrregiaosOld) {
                if (!microrregiaosNew.contains(microrregiaosOldMicrorregiao)) {
                    microrregiaosOldMicrorregiao.setMesorregiao(null);
                    microrregiaosOldMicrorregiao = em.merge(microrregiaosOldMicrorregiao);
                }
            }
            for (Microrregiao microrregiaosNewMicrorregiao : microrregiaosNew) {
                if (!microrregiaosOld.contains(microrregiaosNewMicrorregiao)) {
                    Mesorregiao oldMesorregiaoOfMicrorregiaosNewMicrorregiao = microrregiaosNewMicrorregiao.getMesorregiao();
                    microrregiaosNewMicrorregiao.setMesorregiao(mesorregiao);
                    microrregiaosNewMicrorregiao = em.merge(microrregiaosNewMicrorregiao);
                    if (oldMesorregiaoOfMicrorregiaosNewMicrorregiao != null && !oldMesorregiaoOfMicrorregiaosNewMicrorregiao.equals(mesorregiao)) {
                        oldMesorregiaoOfMicrorregiaosNewMicrorregiao.getMicrorregiaos().remove(microrregiaosNewMicrorregiao);
                        oldMesorregiaoOfMicrorregiaosNewMicrorregiao = em.merge(oldMesorregiaoOfMicrorregiaosNewMicrorregiao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mesorregiao.getIdMesorregiao();
                if (findMesorregiao(id) == null) {
                    throw new NonexistentEntityException("The mesorregiao with id " + id + " no longer exists.");
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
                throw new NonexistentEntityException("The mesorregiao with id " + id + " no longer exists.", enfe);
            }
            Estado estado = mesorregiao.getEstado();
            if (estado != null) {
                estado.getMesorregiaos().remove(mesorregiao);
                estado = em.merge(estado);
            }
            List<Microrregiao> microrregiaos = mesorregiao.getMicrorregiaos();
            for (Microrregiao microrregiaosMicrorregiao : microrregiaos) {
                microrregiaosMicrorregiao.setMesorregiao(null);
                microrregiaosMicrorregiao = em.merge(microrregiaosMicrorregiao);
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

    public List<Mesorregiao> findMesorregiaoEntities(int maxResults, int firstResult) {
        return findMesorregiaoEntities(false, maxResults, firstResult);
    }

    private List<Mesorregiao> findMesorregiaoEntities(boolean all, int maxResults, int firstResult) {
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
