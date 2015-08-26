/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.labican.sabia.dao.EstadoDAO;
import org.labican.sabia.dao.MesorregiaoDAO;

/**
 *
 * @author pablo
 */
public class MesorregiaoTest {
    EstadoDAO edao;
    Mesorregiao m;
    MesorregiaoDAO dao;
    List<Mesorregiao> lista;
    
    @Before
    public void iniciar(){
        edao = new EstadoDAO(org.labican.sabia.util.JPAUtil.EMF);
        dao = new MesorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        m = new Mesorregiao();
        lista = dao.pesquisarTodos();
        
        if(!lista.isEmpty()){
            m = lista.get(0);
        }
        
        m.setCodigoIBGE(1);
        m.setEstado(edao.pesquisarTodos().get(0));
        m.setNome("teste");
        m.setSigla("RN");
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
        m.setNome("safsa");
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
        dao.editar(m);
    }
}