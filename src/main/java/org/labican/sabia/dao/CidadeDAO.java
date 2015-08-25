package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Cidade;

/**
 *
 * @author hyago
 */
public class CidadeDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public CidadeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Cidade cidade) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(cidade);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Cidade cidade) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(cidade);
            dao.getTransaction().commit();
        } catch (Exception ex) {
            throw new Exception("Não foi possível editar", ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void excluir(Integer id) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            Cidade cidade;
            
            try {
                cidade = dao.getReference(Cidade.class, id);
                cidade.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(cidade);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Cidade pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Cidade.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Cidade> pesquisarTodos() {
        return pesquisarCidade(true, -1, -1);
    }
    
    public List<Cidade> pesquisarPaginado(int max, int first) {
        return pesquisarCidade(false, max, first);
    }

    private List<Cidade> pesquisarCidade(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cidade.class));
            Query q = dao.createQuery(cq);
            
            if (!all) {
                q.setMaxResults(max);
                q.setFirstResult(first);
            }
            return q.getResultList();
            
        } finally {
            dao.close();
        }
    }
}
