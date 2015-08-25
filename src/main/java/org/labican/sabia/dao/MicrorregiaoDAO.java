package org.labican.sabia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.labican.sabia.modelo.Microrregiao;

/**
 *
 * @author hyago
 */
public class MicrorregiaoDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public MicrorregiaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Microrregiao microrregiao) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(microrregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Microrregiao microrregiao) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.merge(microrregiao);
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
            Microrregiao microrregiao;
            
            try {
                microrregiao = dao.getReference(Microrregiao.class, id);
                microrregiao.getId();
            } catch (Exception ex) {
                throw new Exception("Não foi possível excluir", ex);
            }
            dao.remove(microrregiao);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public Microrregiao pesquisar(int id) {
        EntityManager dao = getEntityManager();
        
        try {
            return dao.find(Microrregiao.class, id);
        } finally {
            dao.close();
        }
    }
    
    public List<Microrregiao> pesquisarTodos() {
        return pesquisarMicrorregiao(true, -1, -1);
    }
    
    public List<Microrregiao> pesquisarPaginado(int max, int first) {
        return pesquisarMicrorregiao(false, max, first);
    }

    private List<Microrregiao> pesquisarMicrorregiao(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Microrregiao.class));
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
