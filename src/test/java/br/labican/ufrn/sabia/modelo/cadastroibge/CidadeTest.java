package br.labican.ufrn.sabia.modelo.cadastroibge;


import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.cadastroibge.CidadeJpaController;
import br.labican.ufrn.sabia.dao.cadastroibge.MicrorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class CidadeTest {

    Cidade cidade;
    Cidade cidade2;
    Microrregiao microrregiao;
    CidadeJpaController cidadeController;
    MicrorregiaoJpaController microrregiaoController;
    List<Cidade> cidades;
    List<Microrregiao> micros;
    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        cidadeController = new CidadeJpaController(Util.EMF);
        microrregiaoController = new MicrorregiaoJpaController(Util.EMF);

        micros = microrregiaoController.findMicrorregiaoEntities();

        if (!micros.isEmpty()) {
            microrregiao = micros.get(0);
        }

        cidade = new Cidade();
        cidade.setCodIbgeCidade(gerador.nextInt(10000));
        cidade.setNomeCidade(String.valueOf(gerador.nextInt(10000)));
        cidade.setMicrorregiao(microrregiao);

        cidade2 = new Cidade();
        cidade2.setCodIbgeCidade(gerador.nextInt(10000));
        cidade2.setNomeCidade(String.valueOf(gerador.nextInt(10000)));
        cidade2.setMicrorregiao(microrregiao);
    }


    @Test
    public void testInserir() {
        cidadeController.create(cidade);
        cidadeController.create(cidade2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        cidades = cidadeController.findCidadeEntities();

        if (!cidades.isEmpty()) {
            cidade = cidades.get(0);
        }

        cidade.setNomeCidade(String.valueOf(gerador.nextInt(10000)));
        cidadeController.edit(cidade);
    }

    @Test
    public void pesquisar() {
        cidades = cidadeController.findCidadeEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        cidades = cidadeController.findCidadeEntities();

        if (!cidades.isEmpty()) {
            cidade = cidades.get(0);
            cidadeController.destroy(cidade.getIdCidade());
        }


    }
}
