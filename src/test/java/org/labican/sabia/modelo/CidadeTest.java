/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.Before;
import org.labican.sabia.dao.CidadeDAO;
import org.labican.sabia.dao.MicrorregiaoDAO;

/**
 *
 * @author pablo
 */
public class CidadeTest {
    Cidade c;
    CidadeDAO cDAO;
    MicrorregiaoDAO mDAO;
    List<Cidade> cidades;
    
    @Before
    public void iniciar(){
        cDAO = new CidadeDAO(org.labican.sabia.util.JPAUtil.EMF);
        mDAO = new MicrorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        c = new Cidade();
        cidades = cDAO.pesquisarTodos();
    
        if(!cidades.isEmpty()){
            c = cidades.get(0);
        }
        c.setMicrorregiao(mDAO.pesquisarTodos().get(0));
        c.setNome("fgsfsf");
        c.setCodigoIBGE(1);
        
    }
    
    @Test
    public void testInserir(){
        cDAO.criar(c);
    }
    
    @Test
    public void testEditar(){
        cidades = cDAO.pesquisarTodos();
        if(!cidades.isEmpty()){
            c = cidades.get(0);
        }
        c.setNome("24regfg");     
        try {
            cDAO.editar(c);
        } catch (Exception ex) {
            Logger.getLogger(CidadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testPesquisar(){
        cidades = cDAO.pesquisarTodos();
    }
    
    @Test
    public void testExcluir(){
        cidades = cDAO.pesquisarTodos();
        if(!cidades.isEmpty()){
            c = cidades.get(0);
        }        
        try {
            cDAO.excluir(c.getId());
        } catch (Exception ex) {
            Logger.getLogger(CidadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
            
}
