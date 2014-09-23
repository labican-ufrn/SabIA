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
import br.labican.ufrn.sabia.modelo.dadospessoais.Rg;
import br.labican.ufrn.sabia.modelo.dadospessoais.ContatoPessoa;
import br.labican.ufrn.sabia.modelo.dadospessoais.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rummenigge
 */
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        if (pessoa.getContatoPessoas() == null) {
            pessoa.setContatoPessoas(new ArrayList<ContatoPessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rg rg = pessoa.getRg();
            if (rg != null) {
                rg = em.getReference(rg.getClass(), rg.getIdRg());
                pessoa.setRg(rg);
            }
            List<ContatoPessoa> attachedContatoPessoas = new ArrayList<ContatoPessoa>();
            for (ContatoPessoa contatoPessoasContatoPessoaToAttach : pessoa.getContatoPessoas()) {
                contatoPessoasContatoPessoaToAttach = em.getReference(contatoPessoasContatoPessoaToAttach.getClass(), contatoPessoasContatoPessoaToAttach.getIdContatoPessoa());
                attachedContatoPessoas.add(contatoPessoasContatoPessoaToAttach);
            }
            pessoa.setContatoPessoas(attachedContatoPessoas);
            em.persist(pessoa);
            if (rg != null) {
                rg.getPessoas().add(pessoa);
                rg = em.merge(rg);
            }
            for (ContatoPessoa contatoPessoasContatoPessoa : pessoa.getContatoPessoas()) {
                Pessoa oldPessoaOfContatoPessoasContatoPessoa = contatoPessoasContatoPessoa.getPessoa();
                contatoPessoasContatoPessoa.setPessoa(pessoa);
                contatoPessoasContatoPessoa = em.merge(contatoPessoasContatoPessoa);
                if (oldPessoaOfContatoPessoasContatoPessoa != null) {
                    oldPessoaOfContatoPessoasContatoPessoa.getContatoPessoas().remove(contatoPessoasContatoPessoa);
                    oldPessoaOfContatoPessoasContatoPessoa = em.merge(oldPessoaOfContatoPessoasContatoPessoa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getIdPessoa());
            Rg rgOld = persistentPessoa.getRg();
            Rg rgNew = pessoa.getRg();
            List<ContatoPessoa> contatoPessoasOld = persistentPessoa.getContatoPessoas();
            List<ContatoPessoa> contatoPessoasNew = pessoa.getContatoPessoas();
            if (rgNew != null) {
                rgNew = em.getReference(rgNew.getClass(), rgNew.getIdRg());
                pessoa.setRg(rgNew);
            }
            List<ContatoPessoa> attachedContatoPessoasNew = new ArrayList<ContatoPessoa>();
            for (ContatoPessoa contatoPessoasNewContatoPessoaToAttach : contatoPessoasNew) {
                contatoPessoasNewContatoPessoaToAttach = em.getReference(contatoPessoasNewContatoPessoaToAttach.getClass(), contatoPessoasNewContatoPessoaToAttach.getIdContatoPessoa());
                attachedContatoPessoasNew.add(contatoPessoasNewContatoPessoaToAttach);
            }
            contatoPessoasNew = attachedContatoPessoasNew;
            pessoa.setContatoPessoas(contatoPessoasNew);
            pessoa = em.merge(pessoa);
            if (rgOld != null && !rgOld.equals(rgNew)) {
                rgOld.getPessoas().remove(pessoa);
                rgOld = em.merge(rgOld);
            }
            if (rgNew != null && !rgNew.equals(rgOld)) {
                rgNew.getPessoas().add(pessoa);
                rgNew = em.merge(rgNew);
            }
            for (ContatoPessoa contatoPessoasOldContatoPessoa : contatoPessoasOld) {
                if (!contatoPessoasNew.contains(contatoPessoasOldContatoPessoa)) {
                    contatoPessoasOldContatoPessoa.setPessoa(null);
                    contatoPessoasOldContatoPessoa = em.merge(contatoPessoasOldContatoPessoa);
                }
            }
            for (ContatoPessoa contatoPessoasNewContatoPessoa : contatoPessoasNew) {
                if (!contatoPessoasOld.contains(contatoPessoasNewContatoPessoa)) {
                    Pessoa oldPessoaOfContatoPessoasNewContatoPessoa = contatoPessoasNewContatoPessoa.getPessoa();
                    contatoPessoasNewContatoPessoa.setPessoa(pessoa);
                    contatoPessoasNewContatoPessoa = em.merge(contatoPessoasNewContatoPessoa);
                    if (oldPessoaOfContatoPessoasNewContatoPessoa != null && !oldPessoaOfContatoPessoasNewContatoPessoa.equals(pessoa)) {
                        oldPessoaOfContatoPessoasNewContatoPessoa.getContatoPessoas().remove(contatoPessoasNewContatoPessoa);
                        oldPessoaOfContatoPessoasNewContatoPessoa = em.merge(oldPessoaOfContatoPessoasNewContatoPessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getIdPessoa();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
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
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getIdPessoa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            Rg rg = pessoa.getRg();
            if (rg != null) {
                rg.getPessoas().remove(pessoa);
                rg = em.merge(rg);
            }
            List<ContatoPessoa> contatoPessoas = pessoa.getContatoPessoas();
            for (ContatoPessoa contatoPessoasContatoPessoa : contatoPessoas) {
                contatoPessoasContatoPessoa.setPessoa(null);
                contatoPessoasContatoPessoa = em.merge(contatoPessoasContatoPessoa);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
