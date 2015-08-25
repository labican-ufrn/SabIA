package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Endereco;

/**
 *
 * @author hyago
 */
public class EnderecoDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public EnderecoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Endereco endereco) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(endereco);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Endereco endereco) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(endereco);
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
            Endereco endereco;
            
            try {
                endereco = dao.getReference(Endereco.class, id);
                endereco.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(endereco);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Endereco pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Endereco.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Endereco> pesquisarTodos() {
        return pesquisarEndereco(true, -1, -1);
    }
    
    public List<Endereco> pesquisarPaginado(int max, int first) {
        return pesquisarEndereco(false, max, first);
    }

    private List<Endereco> pesquisarEndereco(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereco.class));
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
