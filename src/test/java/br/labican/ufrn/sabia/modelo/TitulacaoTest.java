package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.TitulacaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class TitulacaoTest {


    Titulacao titulacao;
    Titulacao titulacao2;

    TitulacaoJpaController titulacaoController;
    List<Titulacao> Titulacoes;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        titulacaoController = new TitulacaoJpaController(Util.EMF);

        titulacao = new Titulacao();
        titulacao.setNomeTitulacao(String.valueOf(gerador.nextInt(10000)));

        titulacao2 = new Titulacao();
        titulacao2.setNomeTitulacao(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        titulacaoController.create(titulacao);
        titulacaoController.create(titulacao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        Titulacoes = titulacaoController.findTitulacaoEntities();

        if (!Titulacoes.isEmpty()) {
            titulacao = Titulacoes.get(0);
        }

        titulacao.setNomeTitulacao(String.valueOf(gerador.nextInt(10000)));
        titulacaoController.edit(titulacao);
    }

    @Test
    public void pesquisar() {
        Titulacoes = titulacaoController.findTitulacaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        Titulacoes = titulacaoController.findTitulacaoEntities();

        if (!Titulacoes.isEmpty()) {
            titulacao = Titulacoes.get(0);
            titulacaoController.destroy(titulacao.getIdTitulacao());
        }
    }


}
