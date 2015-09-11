package org.labican.sabia.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hyago
 */
public class JpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private Class classe = null;
    
    public JpaController(EntityManagerFactory emf, Class classe) {
        this.emf = emf;
        this.classe = classe;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void criar(Object objeto) {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            dao.persist(objeto);
            dao.getTransaction().commit();
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    public void editar(Object objeto) throws Exception {      
        Method metodo = classe.getMethod("getId");
        
        if(metodo.invoke(objeto) != null){
            EntityManager dao = null;
            
            try {
                dao = getEntityManager();
                dao.getTransaction().begin();
                dao.merge(objeto);
                dao.getTransaction().commit();
            } catch (Exception ex) {
                throw new Exception("Não foi possível editar " + classe.getName(), ex);
            } finally {
                if (dao != null) {
                    dao.close();
                }
            }
        }
    }

    public void excluir(Integer id) throws Exception {
        EntityManager dao = null;
        
        try {
            dao = getEntityManager();
            dao.getTransaction().begin();
            
            Object o;
            o = dao.getReference(classe, id);

            dao.remove(o);
            dao.getTransaction().commit();    
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir " + classe.getName(), ex);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    
    public List pesquisarTodos() {
        return pesquisarObjeto(true, -1, -1);
    }
    
    public List pesquisarPaginado(int max, int first) {
        return pesquisarObjeto(false, max, first);
    }

    private List pesquisarObjeto(boolean all, int max, int first) {
        EntityManager dao = getEntityManager();
        
        try {
            CriteriaQuery cq = dao.getCriteriaBuilder().createQuery();
            cq.select(cq.from(classe));
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
