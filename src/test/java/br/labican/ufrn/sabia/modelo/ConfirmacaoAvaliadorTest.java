package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.AvaliadorJpaController;
import br.labican.ufrn.sabia.dao.ConfirmacaoAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class ConfirmacaoAvaliadorTest {

    ConfirmacaoAvaliador confirmacaoAvaliador;
    ConfirmacaoAvaliador confirmacaoAvaliador1;
    Avaliador avaliador;
    Avaliador avaliador1;

    AvaliadorJpaController avaliadorController;
    ConfirmacaoAvaliadorJpaController confirmacaoAvaliadorController;

    List<ConfirmacaoAvaliador> confirmacoes;
    List<Avaliador> avaliadores;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        confirmacaoAvaliadorController = new ConfirmacaoAvaliadorJpaController(Util.EMF);
        avaliadorController = new AvaliadorJpaController(Util.EMF);

        confirmacaoAvaliador = new ConfirmacaoAvaliador();
        confirmacaoAvaliador1 = new ConfirmacaoAvaliador();

        avaliadores = avaliadorController.findAvaliadorEntities();
        if(!avaliadores.isEmpty()){
            avaliador = avaliadores.get(0);
            avaliador1 = avaliadores.get(1);
        }

        confirmacaoAvaliador.setAvaliador(avaliador);
        confirmacaoAvaliador.setDataConfirmacaoAvaliador(new Date());
        confirmacaoAvaliador.setStatusConfirmacaoAvaliador(String.valueOf(gerador.nextInt(100)));

        confirmacaoAvaliador1.setAvaliador(avaliador1);
        confirmacaoAvaliador1.setDataConfirmacaoAvaliador(new Date());
        confirmacaoAvaliador1.setStatusConfirmacaoAvaliador(String.valueOf(gerador.nextInt(100)));


    }

    @Test
    public void testInserir() {
        confirmacaoAvaliadorController.create(confirmacaoAvaliador);
        confirmacaoAvaliadorController.create(confirmacaoAvaliador);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        confirmacoes = confirmacaoAvaliadorController.findConfirmacaoAvaliadorEntities();

        if (!confirmacoes.isEmpty()) {
            confirmacaoAvaliador = confirmacoes.get(0);
        }

        confirmacaoAvaliador.setStatusConfirmacaoAvaliador(String.valueOf(gerador.nextInt(100)));
        confirmacaoAvaliadorController.edit(confirmacaoAvaliador);
    }

    @Test
    public void pesquisar() {
        confirmacoes = confirmacaoAvaliadorController.findConfirmacaoAvaliadorEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        confirmacoes = confirmacaoAvaliadorController.findConfirmacaoAvaliadorEntities();

        if (!confirmacoes.isEmpty()) {
            confirmacaoAvaliador = confirmacoes.get(0);
        }
        confirmacaoAvaliadorController.destroy(confirmacaoAvaliador.getIdConfirmacaoAvaliador());
    }

}
