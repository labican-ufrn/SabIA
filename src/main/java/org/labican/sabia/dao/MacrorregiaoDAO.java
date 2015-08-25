package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Macrorregiao;

/**
 *
 * @author hyago
 */
public class MacrorregiaoDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public MacrorregiaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Macrorregiao macrorregiao) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(macrorregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Macrorregiao macrorregiao) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(macrorregiao);
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
            Macrorregiao macrorregiao;
            
            try {
                macrorregiao = dao.getReference(Macrorregiao.class, id);
                macrorregiao.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(macrorregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Macrorregiao pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Macrorregiao.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Macrorregiao> pesquisarTodos() {
        return pesquisarMacrorregiao(true, -1, -1);
    }
    
    public List<Macrorregiao> pesquisarPaginado(int max, int first) {
        return pesquisarMacrorregiao(false, max, first);
    }

    private List<Macrorregiao> pesquisarMacrorregiao(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Macrorregiao.class));
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
