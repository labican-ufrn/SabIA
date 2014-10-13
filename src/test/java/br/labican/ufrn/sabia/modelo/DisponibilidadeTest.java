package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.DisponibilidadeJpaController;
import br.labican.ufrn.sabia.dao.SemanaAvaliacaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class DisponibilidadeTest {

    Disponibilidade disponibilidade;
    Disponibilidade disponibilidade2;
    SemanaAvaliacao semanaAvaliacao;

    DisponibilidadeJpaController disponibilidadeController;
    SemanaAvaliacaoJpaController semanaController;

    List<Disponibilidade> disponibilidades;
    List<SemanaAvaliacao> semanaAvaliacoes;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        disponibilidadeController = new DisponibilidadeJpaController(Util.EMF);
        semanaController = new SemanaAvaliacaoJpaController(Util.EMF);

        semanaAvaliacoes = semanaController.findSemanaAvaliacaoEntities();
        if(!semanaAvaliacoes.isEmpty()){
            semanaAvaliacao = semanaAvaliacoes.get(0);
        }

        disponibilidade = new Disponibilidade();
        disponibilidade.setDataDisponibilidade(new Date());
        disponibilidade.setSemanaAvaliacao(semanaAvaliacao);

        disponibilidade2 = new Disponibilidade();
        disponibilidade2.setDataDisponibilidade(new Date());
        disponibilidade2.setSemanaAvaliacao(semanaAvaliacao);


    }

    @Test
    public void testInserir() {
        disponibilidadeController.create(disponibilidade);
        disponibilidadeController.create(disponibilidade2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        disponibilidades = disponibilidadeController.findDisponibilidadeEntities();

        if (!disponibilidades.isEmpty()) {
            disponibilidade = disponibilidades.get(0);
        }

        disponibilidade.setDataDisponibilidade(new Date());
        disponibilidadeController.edit(disponibilidade);
    }

    @Test
    public void pesquisar() {
        disponibilidades = disponibilidadeController.findDisponibilidadeEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        disponibilidades = disponibilidadeController.findDisponibilidadeEntities();

        if (!disponibilidades.isEmpty()) {
            disponibilidade = disponibilidades.get(0);
            disponibilidadeController.destroy(disponibilidade.getIdDisponibilidade());
        }
    }


}
