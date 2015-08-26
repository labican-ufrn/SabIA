/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.labican.sabia.dao.EstadoDAO;
import org.labican.sabia.dao.MacrorregiaoDAO;

/**
 *
 * @author pablo
 */
public class EstadoTest {
    Macrorregiao macro;
    MacrorregiaoDAO mdao;
    Estado e;
    EstadoDAO dao;
    List<Estado> lista;
    
    @Before
    public void iniciar(){
        macro = new Macrorregiao();
        e = new Estado();
        dao = new EstadoDAO(org.labican.sabia.util.JPAUtil.EMF);
        mdao = new MacrorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        lista = dao.pesquisarTodos();
        
        if(!lista.isEmpty()){
            e = lista.get(0);   
        }
        macro = mdao.pesquisarTodos().get(0);
        macro.setCodigoIBGE(1);
        macro.setNome("teste0");
        e.setCodigoIBGE(1);
        e.setMacrorregiao(macro);
        e.setNome("teste");
        e.setSigla("TE");      
    }
    
    @Test
    public void testInserir(){
        dao.criar(e);
    }
    
    @Test
    public void testEditar() throws Exception{
        lista = dao.pesquisarTodos();
        if(!lista.isEmpty()){
            e = lista.get(0);       
        }
        e.setNome("dsfs");
        dao.editar(e);
    }
    
    @Test
    public void testPesquisar(){
        lista = dao.pesquisarTodos();
    }
    
    @Test
    public void testExcliur() throws Exception{
        lista = dao.pesquisarTodos();
        if(!lista.isEmpty()){
            e= lista.get(0);
        }
        dao.excluir(e.getId());
    }
}
