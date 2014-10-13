package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.TipoInstituicaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class TipoInstituicaoTest {


    TipoInstituicao tipoInstituicao;
    TipoInstituicao tipoInstituicao2;

    TipoInstituicaoJpaController tipoInstituicaoController;
    List<TipoInstituicao> Tipos_instituicao;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        tipoInstituicaoController = new TipoInstituicaoJpaController(Util.EMF);

        tipoInstituicao = new TipoInstituicao();
        tipoInstituicao.setNomeTipoInstituicao(String.valueOf(gerador.nextInt(10000)));

        tipoInstituicao2 = new TipoInstituicao();
        tipoInstituicao2.setNomeTipoInstituicao(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        tipoInstituicaoController.create(tipoInstituicao);
        tipoInstituicaoController.create(tipoInstituicao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        Tipos_instituicao = tipoInstituicaoController.findTipoInstituicaoEntities();

        if (!Tipos_instituicao.isEmpty()) {
            tipoInstituicao = Tipos_instituicao.get(0);
        }

        tipoInstituicao.setNomeTipoInstituicao(String.valueOf(gerador.nextInt(10000)));
        tipoInstituicaoController.edit(tipoInstituicao);
    }

    @Test
    public void pesquisar() {
        Tipos_instituicao = tipoInstituicaoController.findTipoInstituicaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        Tipos_instituicao = tipoInstituicaoController.findTipoInstituicaoEntities();

        if (!Tipos_instituicao.isEmpty()) {
            tipoInstituicao = Tipos_instituicao.get(0);
            tipoInstituicaoController.destroy(tipoInstituicao.getIdTipoInstituicao());
        }
    }



}
