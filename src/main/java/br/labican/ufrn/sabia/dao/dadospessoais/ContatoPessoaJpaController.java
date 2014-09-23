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
            Pessoa pessoa = contatoPessoa.getPessoa();
            if (pessoa != null) {
                pessoa = em.getReference(pessoa.getClass(), pessoa.getIdPessoa());
                contatoPessoa.setPessoa(pessoa);
            }
            TipoContato tipoContato = contatoPessoa.getTipoContato();
            if (tipoContato != null) {
                tipoContato = em.getReference(tipoContato.getClass(), tipoContato.getIdTipoContato());
                contatoPessoa.setTipoContato(tipoContato);
            }
            em.persist(contatoPessoa);
            if (pessoa != null) {
                pessoa.getContatoPessoas().add(contatoPessoa);
                pessoa = em.merge(pessoa);
            }
            if (tipoContato != null) {
                tipoContato.getContatoPessoas().add(contatoPessoa);
                tipoContato = em.merge(tipoContato);
            }
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
            ContatoPessoa persistentContatoPessoa = em.find(ContatoPessoa.class, contatoPessoa.getIdContatoPessoa());
            Pessoa pessoaOld = persistentContatoPessoa.getPessoa();
            Pessoa pessoaNew = contatoPessoa.getPessoa();
            TipoContato tipoContatoOld = persistentContatoPessoa.getTipoContato();
            TipoContato tipoContatoNew = contatoPessoa.getTipoContato();
            if (pessoaNew != null) {
                pessoaNew = em.getReference(pessoaNew.getClass(), pessoaNew.getIdPessoa());
                contatoPessoa.setPessoa(pessoaNew);
            }
            if (tipoContatoNew != null) {
                tipoContatoNew = em.getReference(tipoContatoNew.getClass(), tipoContatoNew.getIdTipoContato());
                contatoPessoa.setTipoContato(tipoContatoNew);
            }
            contatoPessoa = em.merge(contatoPessoa);
            if (pessoaOld != null && !pessoaOld.equals(pessoaNew)) {
                pessoaOld.getContatoPessoas().remove(contatoPessoa);
                pessoaOld = em.merge(pessoaOld);
            }
            if (pessoaNew != null && !pessoaNew.equals(pessoaOld)) {
                pessoaNew.getContatoPessoas().add(contatoPessoa);
                pessoaNew = em.merge(pessoaNew);
            }
            if (tipoContatoOld != null && !tipoContatoOld.equals(tipoContatoNew)) {
                tipoContatoOld.getContatoPessoas().remove(contatoPessoa);
                tipoContatoOld = em.merge(tipoContatoOld);
            }
            if (tipoContatoNew != null && !tipoContatoNew.equals(tipoContatoOld)) {
                tipoContatoNew.getContatoPessoas().add(contatoPessoa);
                tipoContatoNew = em.merge(tipoContatoNew);
            }
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
            Pessoa pessoa = contatoPessoa.getPessoa();
            if (pessoa != null) {
                pessoa.getContatoPessoas().remove(contatoPessoa);
                pessoa = em.merge(pessoa);
            }
            TipoContato tipoContato = contatoPessoa.getTipoContato();
            if (tipoContato != null) {
                tipoContato.getContatoPessoas().remove(contatoPessoa);
                tipoContato = em.merge(tipoContato);
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
