package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EnderecoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class EnderecoTest {

    Endereco endereco;
    Endereco endereco1;

    EnderecoJpaController enderecoController;
    List<Endereco> enderecos;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        enderecoController = new EnderecoJpaController(Util.EMF);

        endereco = new Endereco();
        endereco1 = new Endereco();

        endereco.setBairro(String.valueOf(gerador.nextInt(10000)));
        endereco.setComplemento(String.valueOf(gerador.nextInt(10000)));
        endereco.setLogradoudo(String.valueOf(gerador.nextInt(10000)));
        endereco.setNumeroEndereco(String.valueOf(gerador.nextInt(1000)));
        endereco.setPontoReferencia(String.valueOf(gerador.nextInt(10000)));
        endereco.setTipoEndereco(String.valueOf(gerador.nextInt(10000)));

        endereco1.setBairro(String.valueOf(gerador.nextInt(10000)));
        endereco1.setComplemento(String.valueOf(gerador.nextInt(10000)));
        endereco1.setLogradoudo(String.valueOf(gerador.nextInt(10000)));
        endereco1.setNumeroEndereco(String.valueOf(gerador.nextInt(1000)));
        endereco1.setPontoReferencia(String.valueOf(gerador.nextInt(10000)));
        endereco1.setTipoEndereco(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        enderecoController.create(endereco);
        enderecoController.create(endereco1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        enderecos = enderecoController.findEnderecoEntities();

        if (!enderecos.isEmpty()) {
            endereco = enderecos.get(0);
        }

        endereco.setBairro(String.valueOf(gerador.nextInt(10000)));
        enderecoController.edit(endereco);
    }

    @Test
    public void pesquisar() {
        enderecos = enderecoController.findEnderecoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException , Exception {
        enderecos = enderecoController.findEnderecoEntities();

        if (!enderecos.isEmpty()) {
            endereco = enderecos.get(0);
            enderecoController.destroy(endereco.getIdEndereco());
        }
    }


}
