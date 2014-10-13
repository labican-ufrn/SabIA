package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.TipoStatusAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class TipoStatusAvaliadorTest {


    TipoStatusAvaliador tipoStatusAvaliador;
    TipoStatusAvaliador tipoStatusAvaliador2;

    TipoStatusAvaliadorJpaController tipoStatusAvaliadorController;
    List<TipoStatusAvaliador> tipoStatusAvaliadors;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        tipoStatusAvaliadorController = new TipoStatusAvaliadorJpaController(Util.EMF);

        tipoStatusAvaliador = new TipoStatusAvaliador();
        tipoStatusAvaliador.setNomeTipoStatusAvaliador(String.valueOf(gerador.nextInt(10000)));

        tipoStatusAvaliador2 = new TipoStatusAvaliador();
        tipoStatusAvaliador2.setNomeTipoStatusAvaliador(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        tipoStatusAvaliadorController.create(tipoStatusAvaliador);
        tipoStatusAvaliadorController.create(tipoStatusAvaliador2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        tipoStatusAvaliadors = tipoStatusAvaliadorController.findTipoStatusAvaliadorEntities();

        if (!tipoStatusAvaliadors.isEmpty()) {
            tipoStatusAvaliador = tipoStatusAvaliadors.get(0);
        }

        tipoStatusAvaliador.setNomeTipoStatusAvaliador(String.valueOf(gerador.nextInt(10000)));
        tipoStatusAvaliadorController.edit(tipoStatusAvaliador);
    }

    @Test
    public void pesquisar() {
        tipoStatusAvaliadors = tipoStatusAvaliadorController.findTipoStatusAvaliadorEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        tipoStatusAvaliadors = tipoStatusAvaliadorController.findTipoStatusAvaliadorEntities();

        if (!tipoStatusAvaliadors.isEmpty()) {
            tipoStatusAvaliador = tipoStatusAvaliadors.get(0);
            tipoStatusAvaliadorController.destroy(tipoStatusAvaliador.getIdTipoStatusAvaliador());
        }
    }



}
