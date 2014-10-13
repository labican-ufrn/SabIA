package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EnderecoJpaController;
import br.labican.ufrn.sabia.dao.InstituicaoJpaController;
import br.labican.ufrn.sabia.dao.SistemaEnsinoJpaController;
import br.labican.ufrn.sabia.dao.TipoInstituicaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class InstituicaoTest {


    Instituicao instituicao;
    Instituicao instituicao2;
    Endereco endereco;
    SistemaEnsino sistemaEnsino;
    TipoInstituicao tipoInstituicao;

    EnderecoJpaController enderecoController;
    InstituicaoJpaController instituicaoController;
    SistemaEnsinoJpaController sistemaController;
    TipoInstituicaoJpaController tipoInsController;

    List<Instituicao> instituicoes;
    List<Endereco> enderecos;
    List<SistemaEnsino> sistemas;
    List<TipoInstituicao> tiposInstituicao;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        instituicaoController = new InstituicaoJpaController(Util.EMF);
        enderecoController = new EnderecoJpaController(Util.EMF);
        sistemaController = new SistemaEnsinoJpaController(Util.EMF);
        tipoInsController = new TipoInstituicaoJpaController(Util.EMF);

        enderecos = enderecoController.findEnderecoEntities();
        if(!enderecos.isEmpty()){
            endereco = enderecos.get(0);
        }

        tiposInstituicao = tipoInsController.findTipoInstituicaoEntities();
        if(!tiposInstituicao.isEmpty()){
            tipoInstituicao = tiposInstituicao.get(0);
        }

        instituicao = new Instituicao();
        instituicao.setNomeInstituicao(String.valueOf(gerador.nextInt(10000)));
        instituicao.setCodInepInstituicao(gerador.nextInt(10000));
        instituicao.setCodSistecInstituicao(gerador.nextInt(10000));
        instituicao.setEndereco(endereco);
        instituicao.setSistemaEnsino(sistemaEnsino);
        instituicao.setTipoInstituicao(tipoInstituicao);

        instituicao2 = new Instituicao();
        instituicao2.setNomeInstituicao(String.valueOf(gerador.nextInt(10000)));
        instituicao2.setCodInepInstituicao(gerador.nextInt(10000));
        instituicao2.setCodSistecInstituicao(gerador.nextInt(10000));
        instituicao2.setEndereco(endereco);
        instituicao2.setSistemaEnsino(sistemaEnsino);
        instituicao2.setTipoInstituicao(tipoInstituicao);
    }

    @Test
    public void testInserir() {
        instituicaoController.create(instituicao);
        instituicaoController.create(instituicao2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        instituicoes = instituicaoController.findInstituicaoEntities();

        if (!instituicoes.isEmpty()) {
            instituicao = instituicoes.get(0);
        }

        instituicao.setNomeInstituicao(String.valueOf(gerador.nextInt(10000)));
        instituicaoController.edit(instituicao);
    }

    @Test
    public void pesquisar() {
        instituicoes = instituicaoController.findInstituicaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        instituicoes = instituicaoController.findInstituicaoEntities();

        if (!instituicoes.isEmpty()) {
            instituicao = instituicoes.get(0);
            instituicaoController.destroy(instituicao.getIdInstituicao());
        }
    }



}
