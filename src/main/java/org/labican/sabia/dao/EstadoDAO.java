package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Estado;

/**
 *
 * @author hyago
 */
public class EstadoDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public EstadoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Estado estado) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(estado);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Estado estado) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(estado);
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
            Estado estado;
            
            try {
                estado = dao.getReference(Estado.class, id);
                estado.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(estado);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Estado pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Estado.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Estado> pesquisarTodos() {
        return pesquisarEstado(true, -1, -1);
    }
    
    public List<Estado> pesquisarPaginado(int max, int first) {
        return pesquisarEstado(false, max, first);
    }

    private List<Estado> pesquisarEstado(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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
