/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.labican.ufrn.sabia.dao.dadospessoais;

import br.labican.ufrn.sabia.dao.dadospessoais.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.labican.ufrn.sabia.modelo.dadospessoais.ContatoPessoa;
import br.labican.ufrn.sabia.modelo.dadospessoais.TipoContato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
            List<ContatoPessoa> attachedContatoPessoas = new ArrayList<ContatoPessoa>();
            for (ContatoPessoa contatoPessoasContatoPessoaToAttach : tipoContato.getContatoPessoas()) {
                contatoPessoasContatoPessoaToAttach = em.getReference(contatoPessoasContatoPessoaToAttach.getClass(), contatoPessoasContatoPessoaToAttach.getIdContatoPessoa());
                attachedContatoPessoas.add(contatoPessoasContatoPessoaToAttach);
            }
            tipoContato.setContatoPessoas(attachedContatoPessoas);
            em.persist(tipoContato);
            for (ContatoPessoa contatoPessoasContatoPessoa : tipoContato.getContatoPessoas()) {
                TipoContato oldTipoContatoOfContatoPessoasContatoPessoa = contatoPessoasContatoPessoa.getTipoContato();
                contatoPessoasContatoPessoa.setTipoContato(tipoContato);
                contatoPessoasContatoPessoa = em.merge(contatoPessoasContatoPessoa);
                if (oldTipoContatoOfContatoPessoasContatoPessoa != null) {
                    oldTipoContatoOfContatoPessoasContatoPessoa.getContatoPessoas().remove(contatoPessoasContatoPessoa);
                    oldTipoContatoOfContatoPessoasContatoPessoa = em.merge(oldTipoContatoOfContatoPessoasContatoPessoa);
                }
            }
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
            TipoContato persistentTipoContato = em.find(TipoContato.class, tipoContato.getIdTipoContato());
            List<ContatoPessoa> contatoPessoasOld = persistentTipoContato.getContatoPessoas();
            List<ContatoPessoa> contatoPessoasNew = tipoContato.getContatoPessoas();
            List<ContatoPessoa> attachedContatoPessoasNew = new ArrayList<ContatoPessoa>();
            for (ContatoPessoa contatoPessoasNewContatoPessoaToAttach : contatoPessoasNew) {
                contatoPessoasNewContatoPessoaToAttach = em.getReference(contatoPessoasNewContatoPessoaToAttach.getClass(), contatoPessoasNewContatoPessoaToAttach.getIdContatoPessoa());
                attachedContatoPessoasNew.add(contatoPessoasNewContatoPessoaToAttach);
            }
            contatoPessoasNew = attachedContatoPessoasNew;
            tipoContato.setContatoPessoas(contatoPessoasNew);
            tipoContato = em.merge(tipoContato);
            for (ContatoPessoa contatoPessoasOldContatoPessoa : contatoPessoasOld) {
                if (!contatoPessoasNew.contains(contatoPessoasOldContatoPessoa)) {
                    contatoPessoasOldContatoPessoa.setTipoContato(null);
                    contatoPessoasOldContatoPessoa = em.merge(contatoPessoasOldContatoPessoa);
                }
            }
            for (ContatoPessoa contatoPessoasNewContatoPessoa : contatoPessoasNew) {
                if (!contatoPessoasOld.contains(contatoPessoasNewContatoPessoa)) {
                    TipoContato oldTipoContatoOfContatoPessoasNewContatoPessoa = contatoPessoasNewContatoPessoa.getTipoContato();
                    contatoPessoasNewContatoPessoa.setTipoContato(tipoContato);
                    contatoPessoasNewContatoPessoa = em.merge(contatoPessoasNewContatoPessoa);
                    if (oldTipoContatoOfContatoPessoasNewContatoPessoa != null && !oldTipoContatoOfContatoPessoasNewContatoPessoa.equals(tipoContato)) {
                        oldTipoContatoOfContatoPessoasNewContatoPessoa.getContatoPessoas().remove(contatoPessoasNewContatoPessoa);
                        oldTipoContatoOfContatoPessoasNewContatoPessoa = em.merge(oldTipoContatoOfContatoPessoasNewContatoPessoa);
                    }
                }
            }
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
            List<ContatoPessoa> contatoPessoas = tipoContato.getContatoPessoas();
            for (ContatoPessoa contatoPessoasContatoPessoa : contatoPessoas) {
                contatoPessoasContatoPessoa.setTipoContato(null);
                contatoPessoasContatoPessoa = em.merge(contatoPessoasContatoPessoa);
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
