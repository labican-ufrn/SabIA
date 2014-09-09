/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.labican.ufrn.sabia.dao;

import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.cadastroibge.Estado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.cadastroibge.Macrorregiao;
import br.labican.ufrn.sabia.modelo.cadastroibge.Mesorregiao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getMesorregiaos() == null) {
            estado.setMesorregiaos(new ArrayList<Mesorregiao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Macrorregiao macrorregiao = estado.getMacrorregiao();
            if (macrorregiao != null) {
                macrorregiao = em.getReference(macrorregiao.getClass(), macrorregiao.getIdMacrorregiao());
                estado.setMacrorregiao(macrorregiao);
            }
            List<Mesorregiao> attachedMesorregiaos = new ArrayList<Mesorregiao>();
            for (Mesorregiao mesorregiaosMesorregiaoToAttach : estado.getMesorregiaos()) {
                mesorregiaosMesorregiaoToAttach = em.getReference(mesorregiaosMesorregiaoToAttach.getClass(), mesorregiaosMesorregiaoToAttach.getIdMesorregiao());
                attachedMesorregiaos.add(mesorregiaosMesorregiaoToAttach);
            }
            estado.setMesorregiaos(attachedMesorregiaos);
            em.persist(estado);
            if (macrorregiao != null) {
                macrorregiao.getEstados().add(estado);
                macrorregiao = em.merge(macrorregiao);
            }
            for (Mesorregiao mesorregiaosMesorregiao : estado.getMesorregiaos()) {
                Estado oldEstadoOfMesorregiaosMesorregiao = mesorregiaosMesorregiao.getEstado();
                mesorregiaosMesorregiao.setEstado(estado);
                mesorregiaosMesorregiao = em.merge(mesorregiaosMesorregiao);
                if (oldEstadoOfMesorregiaosMesorregiao != null) {
                    oldEstadoOfMesorregiaosMesorregiao.getMesorregiaos().remove(mesorregiaosMesorregiao);
                    oldEstadoOfMesorregiaosMesorregiao = em.merge(oldEstadoOfMesorregiaosMesorregiao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getIdEstado());
            Macrorregiao macrorregiaoOld = persistentEstado.getMacrorregiao();
            Macrorregiao macrorregiaoNew = estado.getMacrorregiao();
            List<Mesorregiao> mesorregiaosOld = persistentEstado.getMesorregiaos();
            List<Mesorregiao> mesorregiaosNew = estado.getMesorregiaos();
            if (macrorregiaoNew != null) {
                macrorregiaoNew = em.getReference(macrorregiaoNew.getClass(), macrorregiaoNew.getIdMacrorregiao());
                estado.setMacrorregiao(macrorregiaoNew);
            }
            List<Mesorregiao> attachedMesorregiaosNew = new ArrayList<Mesorregiao>();
            for (Mesorregiao mesorregiaosNewMesorregiaoToAttach : mesorregiaosNew) {
                mesorregiaosNewMesorregiaoToAttach = em.getReference(mesorregiaosNewMesorregiaoToAttach.getClass(), mesorregiaosNewMesorregiaoToAttach.getIdMesorregiao());
                attachedMesorregiaosNew.add(mesorregiaosNewMesorregiaoToAttach);
            }
            mesorregiaosNew = attachedMesorregiaosNew;
            estado.setMesorregiaos(mesorregiaosNew);
            estado = em.merge(estado);
            if (macrorregiaoOld != null && !macrorregiaoOld.equals(macrorregiaoNew)) {
                macrorregiaoOld.getEstados().remove(estado);
                macrorregiaoOld = em.merge(macrorregiaoOld);
            }
            if (macrorregiaoNew != null && !macrorregiaoNew.equals(macrorregiaoOld)) {
                macrorregiaoNew.getEstados().add(estado);
                macrorregiaoNew = em.merge(macrorregiaoNew);
            }
            for (Mesorregiao mesorregiaosOldMesorregiao : mesorregiaosOld) {
                if (!mesorregiaosNew.contains(mesorregiaosOldMesorregiao)) {
                    mesorregiaosOldMesorregiao.setEstado(null);
                    mesorregiaosOldMesorregiao = em.merge(mesorregiaosOldMesorregiao);
                }
            }
            for (Mesorregiao mesorregiaosNewMesorregiao : mesorregiaosNew) {
                if (!mesorregiaosOld.contains(mesorregiaosNewMesorregiao)) {
                    Estado oldEstadoOfMesorregiaosNewMesorregiao = mesorregiaosNewMesorregiao.getEstado();
                    mesorregiaosNewMesorregiao.setEstado(estado);
                    mesorregiaosNewMesorregiao = em.merge(mesorregiaosNewMesorregiao);
                    if (oldEstadoOfMesorregiaosNewMesorregiao != null && !oldEstadoOfMesorregiaosNewMesorregiao.equals(estado)) {
                        oldEstadoOfMesorregiaosNewMesorregiao.getMesorregiaos().remove(mesorregiaosNewMesorregiao);
                        oldEstadoOfMesorregiaosNewMesorregiao = em.merge(oldEstadoOfMesorregiaosNewMesorregiao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getIdEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            Macrorregiao macrorregiao = estado.getMacrorregiao();
            if (macrorregiao != null) {
                macrorregiao.getEstados().remove(estado);
                macrorregiao = em.merge(macrorregiao);
            }
            List<Mesorregiao> mesorregiaos = estado.getMesorregiaos();
            for (Mesorregiao mesorregiaosMesorregiao : mesorregiaos) {
                mesorregiaosMesorregiao.setEstado(null);
                mesorregiaosMesorregiao = em.merge(mesorregiaosMesorregiao);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
