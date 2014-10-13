package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EixoTecnologicoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;


public class EixoTecnologicoTest {

    EixoTecnologico eixo;
    EixoTecnologico eixo1;

    EixoTecnologicoJpaController eixoController;
    List<EixoTecnologico> eixos;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        eixoController = new EixoTecnologicoJpaController(Util.EMF);


        eixo = new EixoTecnologico();
        eixo1 = new EixoTecnologico();

        eixo.setNomeEixoTecnologico(String.valueOf(gerador.nextInt(10000)));
        eixo1.setNomeEixoTecnologico(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir()  throws NonexistentEntityException, Exception {
        eixoController.create(eixo);
        eixoController.create(eixo1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        eixos = eixoController.findEixoTecnologicoEntities();

        if (!eixos.isEmpty()) {
            eixo = eixos.get(0);
        }

        eixo.setNomeEixoTecnologico(String.valueOf(gerador.nextInt(10000)));
        eixoController.edit(eixo);
    }

    @Test
    public void pesquisar() {
        eixos = eixoController.findEixoTecnologicoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException , Exception {
        eixos = eixoController.findEixoTecnologicoEntities();

        if (!eixos.isEmpty()) {
            eixo = eixos.get(0);
            eixoController.destroy(eixo.getIdEixoTecnologico());
        }
    }
}
