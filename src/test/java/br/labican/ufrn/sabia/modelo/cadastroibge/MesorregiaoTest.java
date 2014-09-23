package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.cadastroibge.EstadoJpaController;
import br.labican.ufrn.sabia.dao.cadastroibge.MesorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class MesorregiaoTest {

    Mesorregiao mesorregiao;
    Mesorregiao mesorregiao2;
    MesorregiaoJpaController mesorregiaoController;
    EstadoJpaController estadoController;
    Estado estado;
    List<Estado> estados;
    List<Mesorregiao> mesos;
    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        mesorregiaoController = new MesorregiaoJpaController(Util.EMF);
        estadoController = new EstadoJpaController(Util.EMF);

        estados = estadoController.findEstadoEntities();

        if (!estados.isEmpty()) {
            estado = estados.get(0);
        }

        mesorregiao = new Mesorregiao();
        mesorregiao.setCodIbgeMesorregiao(gerador.nextInt(10000));
        mesorregiao.setEstado(estado);
        mesorregiao.setNomeMesorregiao(String.valueOf(gerador.nextInt(10000)));
        mesorregiao.setSiglaMesorregiao(String.valueOf(gerador.nextInt(10000)));

        mesorregiao2 = new Mesorregiao();
        mesorregiao.setCodIbgeMesorregiao(gerador.nextInt(10000));
        mesorregiao.setEstado(estado);
        mesorregiao.setNomeMesorregiao(String.valueOf(gerador.nextInt(10000)));
        mesorregiao.setSiglaMesorregiao(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        mesorregiaoController.create(mesorregiao);
        mesorregiaoController.create(mesorregiao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        mesos = mesorregiaoController.findMesorregiaoEntities();

        if (!mesos.isEmpty()) {
            mesorregiao = mesos.get(0);
        }

        mesorregiao.setNomeMesorregiao(String.valueOf(gerador.nextInt(10000)));
        mesorregiao.setCodIbgeMesorregiao(gerador.nextInt(10000));
        mesorregiaoController.edit(mesorregiao);
    }

    @Test
    public void pesquisar() {
        mesos = mesorregiaoController.findMesorregiaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        mesos = mesorregiaoController.findMesorregiaoEntities();

        if (!mesos.isEmpty()) {
            mesorregiao = mesos.get(0);
            mesorregiaoController.destroy(mesorregiao.getIdMesorregiao());
        }
    }
}