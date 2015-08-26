/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo;

import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.labican.sabia.dao.CidadeDAO;
import org.labican.sabia.dao.EnderecoDAO;

/**
 *
 * @author pablo
 */
public class EnderecoTest {
    Endereco e;
    EnderecoDAO eDAO;
    CidadeDAO cDAO;
    List<Endereco> enderecos;
    
    @Before
    public void iniciar(){
        eDAO = new EnderecoDAO(org.labican.sabia.util.JPAUtil.EMF);
        cDAO = new CidadeDAO(org.labican.sabia.util.JPAUtil.EMF);
        e = new Endereco();
        enderecos = eDAO.pesquisarTodos();
        
        if(!enderecos.isEmpty()){
            e = enderecos.get(0);
        }
        e.setBairro("teste1");
        e.setCidade(cDAO.pesquisarTodos().get(0));
        e.setComplemento("teste2");
        e.setLogradouro("teste3");
        e.setNumero("teste4");
        e.setPontoReferencia("teste5");
        e.setTipo("teste6");
    }
    
    @Test
    public void testInserir(){
        eDAO.criar(e);
    }
    
    @Test
    public void testEditar() throws Exception{
        enderecos = eDAO.pesquisarTodos();
        if(!enderecos.isEmpty()){
            e = enderecos.get(0);        
        }
        e.setBairro("dsfs");
         eDAO.editar(e);
    }
    
    @Test
    public void testPesquisar(){
        enderecos = eDAO.pesquisarTodos();
    }
    
    @Test
    public void testExcluir() throws Exception{
        enderecos = eDAO.pesquisarTodos();
        if(!enderecos.isEmpty()){
            e = enderecos.get(0);           
        }    
        eDAO.excluir(e.getId());
    }
}