/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.labican.sabia.dao.MesorregiaoDAO;
import org.labican.sabia.dao.MicrorregiaoDAO;

/**
 *
 * @author pablo
 */
public class MicrorregiaoTest {
    MesorregiaoDAO mdao;
    MicrorregiaoDAO dao;
    Microrregiao m;
    List<Microrregiao> lista;
    
    @Before
    public void iniciar(){
        mdao = new MesorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        dao = new MicrorregiaoDAO(org.labican.sabia.util.JPAUtil.EMF);
        m = new Microrregiao();
        lista = dao.pesquisarTodos();
        
        if(!lista.isEmpty()){
            m = lista.get(0);
        }
        
        m.setCodigoIBGE(1);
        m.setMesorregiao(mdao.pesquisarTodos().get(0));
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
        
        m.setNome("223");
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
