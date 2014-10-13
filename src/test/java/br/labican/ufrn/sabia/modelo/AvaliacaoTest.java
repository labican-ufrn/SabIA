package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.AvaliacaoJpaController;
import br.labican.ufrn.sabia.dao.EquipeJpaController;
import br.labican.ufrn.sabia.dao.InstituicaoJpaController;
import br.labican.ufrn.sabia.dao.ProjetoJpaController;
import br.labican.ufrn.sabia.dao.SemanaAvaliacaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class AvaliacaoTest {

    Avaliacao avaliacao;
    Avaliacao avaliacao2;
    Equipe equipe;
    Instituicao instituicao;
    Projeto projeto;
    SemanaAvaliacao semanaAvaliacao;

    SemanaAvaliacaoJpaController semanaController;
    AvaliacaoJpaController avaliacaoController;
    EquipeJpaController equipeController;
    InstituicaoJpaController instituicaoController;
    ProjetoJpaController projetoController;

    List<Avaliacao> avaliacoes;
    List<Equipe> equipes;
    List<Instituicao> instituicoes;
    List<Projeto> projetos;
    List<SemanaAvaliacao> semanas;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        avaliacaoController = new AvaliacaoJpaController(Util.EMF);
        instituicaoController = new InstituicaoJpaController(Util.EMF);
        projetoController = new ProjetoJpaController(Util.EMF);
        semanaController = new SemanaAvaliacaoJpaController(Util.EMF);

        equipes = equipeController.findEquipeEntities();

        if (!equipes.isEmpty()) {
            equipe = equipes.get(0);
        }

        instituicoes = instituicaoController.findInstituicaoEntities();

        if (!instituicoes.isEmpty()) {
            instituicao = instituicoes.get(0);
        }

        projetos = projetoController.findProjetoEntities();

        if (!projetos.isEmpty()) {
            projeto = projetos.get(0);
        }

        semanas = semanaController.findSemanaAvaliacaoEntities();

        if (!semanas.isEmpty()) {
            semanaAvaliacao = semanas.get(0);
        }

        avaliacao = new Avaliacao();
        avaliacao.setEquipe(equipe);
        avaliacao.setInstituicao(instituicao);
        avaliacao.setProjeto(projeto);
        avaliacao.setSemanaAvaliacao(semanaAvaliacao);

        avaliacao2 = new Avaliacao();
        avaliacao2.setEquipe(equipe);
        avaliacao2.setInstituicao(instituicao);
        avaliacao2.setProjeto(projeto);
        avaliacao2.setSemanaAvaliacao(semanaAvaliacao);
    }

    @Test
    public void testInserir() {
        avaliacaoController.create(avaliacao);
        avaliacaoController.create(avaliacao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        avaliacoes = avaliacaoController.findAvaliacaoEntities();

        if (!avaliacoes.isEmpty()) {
            avaliacao = avaliacoes.get(0);
        }

        equipes = equipeController.findEquipeEntities();

        if (!equipes.isEmpty()) {
            equipe = equipes.get(0);
        }

        avaliacao.setEquipe(equipe);
        avaliacaoController.edit(avaliacao);
    }

    @Test
    public void pesquisar() {
        avaliacoes = avaliacaoController.findAvaliacaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        avaliacoes = avaliacaoController.findAvaliacaoEntities();

        if (!avaliacoes.isEmpty()) {
            avaliacao = avaliacoes.get(0);
            avaliacaoController.destroy(avaliacao.getIdAvaliacao());
        }
    }

}
