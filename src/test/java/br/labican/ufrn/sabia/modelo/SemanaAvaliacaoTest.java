package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.SemanaAvaliacaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class SemanaAvaliacaoTest {

    SemanaAvaliacao semanaAvaliacao;
    SemanaAvaliacao semanaAvaliacao2;

    SemanaAvaliacaoJpaController semanaAvaliacaoController;
    List<SemanaAvaliacao> semanaAvaliacoes;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        semanaAvaliacaoController = new SemanaAvaliacaoJpaController(Util.EMF);

        semanaAvaliacao = new SemanaAvaliacao();
        semanaAvaliacao.setDataFimSemanaAvalicao(new Date());
        semanaAvaliacao.setDataInicioSemanaAvalicao(new Date());
        semanaAvaliacao.setNumeroSemanaAvaliacao(gerador.nextInt(10000));

        semanaAvaliacao2 = new SemanaAvaliacao();
        semanaAvaliacao2.setDataFimSemanaAvalicao(new Date());
        semanaAvaliacao2.setDataInicioSemanaAvalicao(new Date());
        semanaAvaliacao2.setNumeroSemanaAvaliacao(gerador.nextInt(10000));
    }

    @Test
    public void testInserir() {
        semanaAvaliacaoController.create(semanaAvaliacao);
        semanaAvaliacaoController.create(semanaAvaliacao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        semanaAvaliacoes = semanaAvaliacaoController.findSemanaAvaliacaoEntities();

        if (!semanaAvaliacoes.isEmpty()) {
            semanaAvaliacao = semanaAvaliacoes.get(0);
        }

        semanaAvaliacao.setNumeroSemanaAvaliacao(gerador.nextInt(10000));
        semanaAvaliacaoController.edit(semanaAvaliacao);
    }

    @Test
    public void pesquisar() {
        semanaAvaliacoes = semanaAvaliacaoController.findSemanaAvaliacaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        semanaAvaliacoes = semanaAvaliacaoController.findSemanaAvaliacaoEntities();

        if (!semanaAvaliacoes.isEmpty()) {
            semanaAvaliacao = semanaAvaliacoes.get(0);
            semanaAvaliacaoController.destroy(semanaAvaliacao.getIdSemanaAvaliacao());
        }
    }


}
