/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.labican.sabia.dao.MacrorregiaoDAO;

/**
 *
 * @author pablo
 */
public class MacrorregiaoTest {
    Macrorregiao m;
    List<Macrorregiao> lista;
    MacrorregiaoDAO dao;
    
    @Before
    public void iniciar(){
        m = new Macrorregiao();
        dao = new MacrorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        lista = dao.pesquisarTodos();
        
        if(!lista.isEmpty()){
            m = lista.get(0);
        }
        
        m.setCodigoIBGE(1);
        m.setNome("teste");
    }

    @Test
    public void testInserir(){
        dao.criar(m);
    }
    
    @Test
    public void testEditar() throws Exception{
        lista = dao.pesquisarTodos();
        if(!lista.isEmpty()){
            m = lista.get(0);
        }
        m.setNome("teste2");
        dao.editar(m);
    }
    
    @Test
    public void testPesquisar(){
        lista = dao.pesquisarTodos();
    }
    
    @Test
    public void testExcluir() throws Exception{
        lista = dao.pesquisarTodos();
        if(!lista.isEmpty()){
            m = lista.get(0);
        }
        dao.excluir(m.getId());
    }
    
}
