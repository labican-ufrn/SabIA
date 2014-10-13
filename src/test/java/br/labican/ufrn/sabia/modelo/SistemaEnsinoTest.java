package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.SistemaEnsinoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class SistemaEnsinoTest {


    SistemaEnsino sistemaEnsino;
    SistemaEnsino sistemaEnsino2;

    SistemaEnsinoJpaController sistemaEnsinoController;
    List<SistemaEnsino> sistemasEnsino;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        sistemaEnsinoController = new SistemaEnsinoJpaController(Util.EMF);

        sistemaEnsino = new SistemaEnsino();
        sistemaEnsino.setNomeSistemaEnsino(String.valueOf(gerador.nextInt(10000)));

        sistemaEnsino2 = new SistemaEnsino();
        sistemaEnsino2.setNomeSistemaEnsino(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        sistemaEnsinoController.create(sistemaEnsino);
        sistemaEnsinoController.create(sistemaEnsino2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        sistemasEnsino = sistemaEnsinoController.findSistemaEnsinoEntities();

        if (!sistemasEnsino.isEmpty()) {
            sistemaEnsino = sistemasEnsino.get(0);
        }

        sistemaEnsino.setNomeSistemaEnsino(String.valueOf(gerador.nextInt(10000)));
        sistemaEnsinoController.edit(sistemaEnsino);
    }

    @Test
    public void pesquisar() {
        sistemasEnsino = sistemaEnsinoController.findSistemaEnsinoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        sistemasEnsino = sistemaEnsinoController.findSistemaEnsinoEntities();

        if (!sistemasEnsino.isEmpty()) {
            sistemaEnsino = sistemasEnsino.get(0);
            sistemaEnsinoController.destroy(sistemaEnsino.getIdSistemaEnsino());
        }
    }



}
