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
        if (microrregiao.getCidades() == null) {
            microrregiao.setCidades(new ArrayList<Cidade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mesorregiao mesorregiao = microrregiao.getMesorregiao();
            if (mesorregiao != null) {
                mesorregiao = em.getReference(mesorregiao.getClass(), mesorregiao.getIdMesorregiao());
                microrregiao.setMesorregiao(mesorregiao);
            }
            List<Cidade> attachedCidades = new ArrayList<Cidade>();
            for (Cidade cidadesCidadeToAttach : microrregiao.getCidades()) {
                cidadesCidadeToAttach = em.getReference(cidadesCidadeToAttach.getClass(), cidadesCidadeToAttach.getIdCidade());
                attachedCidades.add(cidadesCidadeToAttach);
            }
            microrregiao.setCidades(attachedCidades);
            em.persist(microrregiao);
            if (mesorregiao != null) {
                mesorregiao.getMicrorregiaos().add(microrregiao);
                mesorregiao = em.merge(mesorregiao);
            }
            for (Cidade cidadesCidade : microrregiao.getCidades()) {
                Microrregiao oldMicrorregiaoOfCidadesCidade = cidadesCidade.getMicrorregiao();
                cidadesCidade.setMicrorregiao(microrregiao);
                cidadesCidade = em.merge(cidadesCidade);
                if (oldMicrorregiaoOfCidadesCidade != null) {
                    oldMicrorregiaoOfCidadesCidade.getCidades().remove(cidadesCidade);
                    oldMicrorregiaoOfCidadesCidade = em.merge(oldMicrorregiaoOfCidadesCidade);
                }
            }
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
            Microrregiao persistentMicrorregiao = em.find(Microrregiao.class, microrregiao.getIdMicrorregiao());
            Mesorregiao mesorregiaoOld = persistentMicrorregiao.getMesorregiao();
            Mesorregiao mesorregiaoNew = microrregiao.getMesorregiao();
            List<Cidade> cidadesOld = persistentMicrorregiao.getCidades();
            List<Cidade> cidadesNew = microrregiao.getCidades();
            if (mesorregiaoNew != null) {
                mesorregiaoNew = em.getReference(mesorregiaoNew.getClass(), mesorregiaoNew.getIdMesorregiao());
                microrregiao.setMesorregiao(mesorregiaoNew);
            }
            List<Cidade> attachedCidadesNew = new ArrayList<Cidade>();
            for (Cidade cidadesNewCidadeToAttach : cidadesNew) {
                cidadesNewCidadeToAttach = em.getReference(cidadesNewCidadeToAttach.getClass(), cidadesNewCidadeToAttach.getIdCidade());
                attachedCidadesNew.add(cidadesNewCidadeToAttach);
            }
            cidadesNew = attachedCidadesNew;
            microrregiao.setCidades(cidadesNew);
            microrregiao = em.merge(microrregiao);
            if (mesorregiaoOld != null && !mesorregiaoOld.equals(mesorregiaoNew)) {
                mesorregiaoOld.getMicrorregiaos().remove(microrregiao);
                mesorregiaoOld = em.merge(mesorregiaoOld);
            }
            if (mesorregiaoNew != null && !mesorregiaoNew.equals(mesorregiaoOld)) {
                mesorregiaoNew.getMicrorregiaos().add(microrregiao);
                mesorregiaoNew = em.merge(mesorregiaoNew);
            }
            for (Cidade cidadesOldCidade : cidadesOld) {
                if (!cidadesNew.contains(cidadesOldCidade)) {
                    cidadesOldCidade.setMicrorregiao(null);
                    cidadesOldCidade = em.merge(cidadesOldCidade);
                }
            }
            for (Cidade cidadesNewCidade : cidadesNew) {
                if (!cidadesOld.contains(cidadesNewCidade)) {
                    Microrregiao oldMicrorregiaoOfCidadesNewCidade = cidadesNewCidade.getMicrorregiao();
                    cidadesNewCidade.setMicrorregiao(microrregiao);
                    cidadesNewCidade = em.merge(cidadesNewCidade);
                    if (oldMicrorregiaoOfCidadesNewCidade != null && !oldMicrorregiaoOfCidadesNewCidade.equals(microrregiao)) {
                        oldMicrorregiaoOfCidadesNewCidade.getCidades().remove(cidadesNewCidade);
                        oldMicrorregiaoOfCidadesNewCidade = em.merge(oldMicrorregiaoOfCidadesNewCidade);
                    }
                }
            }
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
            Mesorregiao mesorregiao = microrregiao.getMesorregiao();
            if (mesorregiao != null) {
                mesorregiao.getMicrorregiaos().remove(microrregiao);
                mesorregiao = em.merge(mesorregiao);
            }
            List<Cidade> cidades = microrregiao.getCidades();
            for (Cidade cidadesCidade : cidades) {
                cidadesCidade.setMicrorregiao(null);
                cidadesCidade = em.merge(cidadesCidade);
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
