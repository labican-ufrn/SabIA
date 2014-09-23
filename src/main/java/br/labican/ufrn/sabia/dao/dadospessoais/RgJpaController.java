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
import br.labican.ufrn.sabia.modelo.dadospessoais.Pessoa;
import br.labican.ufrn.sabia.modelo.dadospessoais.Rg;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class RgJpaController implements Serializable {

    public RgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rg rg) {
        if (rg.getPessoas() == null) {
            rg.setPessoas(new ArrayList<Pessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pessoa> attachedPessoas = new ArrayList<Pessoa>();
            for (Pessoa pessoasPessoaToAttach : rg.getPessoas()) {
                pessoasPessoaToAttach = em.getReference(pessoasPessoaToAttach.getClass(), pessoasPessoaToAttach.getIdPessoa());
                attachedPessoas.add(pessoasPessoaToAttach);
            }
            rg.setPessoas(attachedPessoas);
            em.persist(rg);
            for (Pessoa pessoasPessoa : rg.getPessoas()) {
                Rg oldRgOfPessoasPessoa = pessoasPessoa.getRg();
                pessoasPessoa.setRg(rg);
                pessoasPessoa = em.merge(pessoasPessoa);
                if (oldRgOfPessoasPessoa != null) {
                    oldRgOfPessoasPessoa.getPessoas().remove(pessoasPessoa);
                    oldRgOfPessoasPessoa = em.merge(oldRgOfPessoasPessoa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rg rg) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rg persistentRg = em.find(Rg.class, rg.getIdRg());
            List<Pessoa> pessoasOld = persistentRg.getPessoas();
            List<Pessoa> pessoasNew = rg.getPessoas();
            List<Pessoa> attachedPessoasNew = new ArrayList<Pessoa>();
            for (Pessoa pessoasNewPessoaToAttach : pessoasNew) {
                pessoasNewPessoaToAttach = em.getReference(pessoasNewPessoaToAttach.getClass(), pessoasNewPessoaToAttach.getIdPessoa());
                attachedPessoasNew.add(pessoasNewPessoaToAttach);
            }
            pessoasNew = attachedPessoasNew;
            rg.setPessoas(pessoasNew);
            rg = em.merge(rg);
            for (Pessoa pessoasOldPessoa : pessoasOld) {
                if (!pessoasNew.contains(pessoasOldPessoa)) {
                    pessoasOldPessoa.setRg(null);
                    pessoasOldPessoa = em.merge(pessoasOldPessoa);
                }
            }
            for (Pessoa pessoasNewPessoa : pessoasNew) {
                if (!pessoasOld.contains(pessoasNewPessoa)) {
                    Rg oldRgOfPessoasNewPessoa = pessoasNewPessoa.getRg();
                    pessoasNewPessoa.setRg(rg);
                    pessoasNewPessoa = em.merge(pessoasNewPessoa);
                    if (oldRgOfPessoasNewPessoa != null && !oldRgOfPessoasNewPessoa.equals(rg)) {
                        oldRgOfPessoasNewPessoa.getPessoas().remove(pessoasNewPessoa);
                        oldRgOfPessoasNewPessoa = em.merge(oldRgOfPessoasNewPessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rg.getIdRg();
                if (findRg(id) == null) {
                    throw new NonexistentEntityException("The rg with id " + id + " no longer exists.");
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
            Rg rg;
            try {
                rg = em.getReference(Rg.class, id);
                rg.getIdRg();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rg with id " + id + " no longer exists.", enfe);
            }
            List<Pessoa> pessoas = rg.getPessoas();
            for (Pessoa pessoasPessoa : pessoas) {
                pessoasPessoa.setRg(null);
                pessoasPessoa = em.merge(pessoasPessoa);
            }
            em.remove(rg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rg> findRgEntities() {
        return findRgEntities(true, -1, -1);
    }

    public List<Rg> findRgEntities(int maxResults, int firstResult) {
        return findRgEntities(false, maxResults, firstResult);
    }

    private List<Rg> findRgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rg.class));
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

    public Rg findRg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rg.class, id);
        } finally {
            em.close();
        }
    }

    public int getRgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rg> rt = cq.from(Rg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
