package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Mesorregiao;

/**
 *
 * @author hyago
 */
public class MesorregiaoDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public MesorregiaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Mesorregiao mesorregiao) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(mesorregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Mesorregiao mesorregiao) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(mesorregiao);
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
            Mesorregiao mesorregiao;
            
            try {
                mesorregiao = dao.getReference(Mesorregiao.class, id);
                mesorregiao.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(mesorregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Mesorregiao pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Mesorregiao.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Mesorregiao> pesquisarTodos() {
        return pesquisarMesorregiao(true, -1, -1);
    }
    
    public List<Mesorregiao> pesquisarPaginado(int max, int first) {
        return pesquisarMesorregiao(false, max, first);
    }

    private List<Mesorregiao> pesquisarMesorregiao(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mesorregiao.class));
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
