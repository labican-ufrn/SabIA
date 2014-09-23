package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.cadastroibge.EstadoJpaController;
import br.labican.ufrn.sabia.dao.cadastroibge.MacrorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class EstadoTest {

    Estado estado;
    Estado estado2;
    Macrorregiao macrorregiao;

    MacrorregiaoJpaController macrorregiaoController;
    EstadoJpaController estadoController;
    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        estadoController = new EstadoJpaController(Util.EMF);
        macrorregiaoController = new MacrorregiaoJpaController(Util.EMF);

        List<Macrorregiao> macros = macrorregiaoController
                .findMacrorregiaoEntities();

        if (!macros.isEmpty()) {
            macrorregiao = macros.get(0);
        }

        estado = new Estado();
        estado.setCodIbgeEstado(gerador.nextInt(10000));
        estado.setMacrorregiao(macrorregiao);
        estado.setNomeEstado(String.valueOf(gerador.nextInt(10000)));
        estado.setSiglaEstado(String.valueOf(gerador.nextInt(10000)));

        estado2 = new Estado();
        estado2.setCodIbgeEstado(gerador.nextInt(10000));
        estado2.setMacrorregiao(macrorregiao);
        estado2.setNomeEstado(String.valueOf(gerador.nextInt(10000)));
        estado2.setSiglaEstado(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        estadoController.create(estado);
        estadoController.create(estado2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        List<Estado> estados = estadoController.findEstadoEntities();

        if (!estados.isEmpty()) {
            estado = estados.get(0);
        }

        estado.setNomeEstado(String.valueOf(gerador.nextInt(10000)));
        estado.setCodIbgeEstado(gerador.nextInt(10000));
        estadoController.edit(estado);
    }

    @Test
    public void pesquisar() {
        List<Estado> estados = estadoController.findEstadoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        List<Estado> estados = estadoController.findEstadoEntities();

        if (!estados.isEmpty()) {
            estado = estados.get(0);
            estadoController.destroy(estado.getIdEstado());
        }
    }
}
