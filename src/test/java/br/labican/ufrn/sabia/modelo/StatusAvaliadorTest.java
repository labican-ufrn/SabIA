package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.StatusAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.TipoStatusAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class StatusAvaliadorTest {


    StatusAvaliador statusAvaliador;
    StatusAvaliador statusAvaliador2;
    TipoStatusAvaliador tipoStatus;

    StatusAvaliadorJpaController statusAvaliadorController;
    TipoStatusAvaliadorJpaController statusController;

    List<StatusAvaliador> status_Avaliador;
    List<TipoStatusAvaliador> tipos_status;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        statusAvaliadorController = new StatusAvaliadorJpaController(Util.EMF);
        statusController = new TipoStatusAvaliadorJpaController(Util.EMF);

        tipos_status = statusController.findTipoStatusAvaliadorEntities();
        if(!tipos_status.isEmpty()){
            tipoStatus = tipos_status.get(0);
        }

        statusAvaliador = new StatusAvaliador();
        statusAvaliador.setDataStatusAvaliador(new Date());
        statusAvaliador.setJustificativaStatusAvaliador(String.valueOf(gerador.nextInt(10000)));
        statusAvaliador.setTipoStatusAvaliador(tipoStatus);

        statusAvaliador2 = new StatusAvaliador();
        statusAvaliador2.setDataStatusAvaliador(new Date());
        statusAvaliador2.setJustificativaStatusAvaliador(String.valueOf(gerador.nextInt(10000)));
        statusAvaliador2.setTipoStatusAvaliador(tipoStatus);
    }

    @Test
    public void testInserir() {
        statusAvaliadorController.create(statusAvaliador);
        statusAvaliadorController.create(statusAvaliador2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        status_Avaliador = statusAvaliadorController.findStatusAvaliadorEntities();

        if (!status_Avaliador.isEmpty()) {
            statusAvaliador = status_Avaliador.get(0);
        }

        statusAvaliador.setJustificativaStatusAvaliador(String.valueOf(gerador.nextInt(10000)));
        statusAvaliadorController.edit(statusAvaliador);
    }

    @Test
    public void pesquisar() {
        status_Avaliador = statusAvaliadorController.findStatusAvaliadorEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        status_Avaliador = statusAvaliadorController.findStatusAvaliadorEntities();

        if (!status_Avaliador.isEmpty()) {
            statusAvaliador = status_Avaliador.get(0);
            statusAvaliadorController.destroy(statusAvaliador.getIdStatusAvaliador());
        }
    }



}
