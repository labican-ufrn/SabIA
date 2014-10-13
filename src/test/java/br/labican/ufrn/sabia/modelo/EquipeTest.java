package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.AvaliacaoJpaController;
import br.labican.ufrn.sabia.dao.EquipeJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class EquipeTest {

    Equipe equipe;
    Equipe equipe2;
    Avaliacao avaliacao;
    Avaliacao avaliacao2;

    AvaliacaoJpaController avaliacaoController;
    EquipeJpaController equipeController;
    List<Equipe> equipes;
    List<Avaliacao> avaliacoes;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        equipeController = new EquipeJpaController(Util.EMF);
        avaliacaoController = new AvaliacaoJpaController(Util.EMF);

        avaliacoes = avaliacaoController.findAvaliacaoEntities();
        if(!avaliacoes.isEmpty()){
            avaliacao = avaliacoes.get(0);
            avaliacao2 = avaliacoes.get(1);
        }

        equipe = new Equipe();
        equipe.setAvaliacao(avaliacao);

        equipe2 = new Equipe();
        equipe2.setAvaliacao(avaliacao2);

    }

    @Test
    public void testInserir() {
        equipeController.create(equipe);
        equipeController.create(equipe2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        equipes = equipeController.findEquipeEntities();

        avaliacoes = avaliacaoController.findAvaliacaoEntities();
        if(!avaliacoes.isEmpty()){
            avaliacao = avaliacoes.get(0);
        }

        if (!equipes.isEmpty()) {
            equipe = equipes.get(0);
        }

        equipe.setAvaliacao(avaliacao);
        equipeController.edit(equipe);
    }

    @Test
    public void pesquisar() {
        equipes = equipeController.findEquipeEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        equipes = equipeController.findEquipeEntities();

        if (!equipes.isEmpty()) {
            equipe = equipes.get(0);
            equipeController.destroy(equipe.getIdEquipe());
        }
    }


}
