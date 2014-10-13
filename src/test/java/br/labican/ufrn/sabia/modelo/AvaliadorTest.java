package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.AvaliadorJpaController;
import br.labican.ufrn.sabia.dao.CargoJpaController;
import br.labican.ufrn.sabia.dao.InstituicaoJpaController;
import br.labican.ufrn.sabia.dao.StatusAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.PessoaJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.dadospessoais.Pessoa;
import br.labican.ufrn.sabia.util.Util;

public class AvaliadorTest {

    Avaliador avaliador;
    Avaliador avaliador1;
    Cargo cargo;
    Pessoa pessoa;
    Instituicao instituicao;
    StatusAvaliador status;

    AvaliadorJpaController avaliadorController;
    CargoJpaController cargoController;
    PessoaJpaController pessoaController;
    InstituicaoJpaController instituicaoController;
    StatusAvaliadorJpaController statusController;

    List<Avaliador> avaliadores;
    List<Cargo> cargos;
    List<Pessoa> pessoas;
    List<Instituicao> instituicoes;
    List<StatusAvaliador> status_s;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        avaliadorController = new AvaliadorJpaController(Util.EMF);
        cargoController = new CargoJpaController(Util.EMF);
        pessoaController = new PessoaJpaController(Util.EMF);
        instituicaoController = new InstituicaoJpaController(Util.EMF);

        cargos = cargoController.findCargoEntities();
        if (!cargos.isEmpty()) {
            cargo = cargos.get(0);
        }

        pessoas = pessoaController.findPessoaEntities();
        if (!pessoas.isEmpty()) {
            pessoa = pessoas.get(0);
        }

        instituicoes = instituicaoController.findInstituicaoEntities();
        if (!instituicoes.isEmpty()) {
            instituicao = instituicoes.get(0);
        }

        status_s = statusController.findStatusAvaliadorEntities();
        if (!status_s.isEmpty()) {
            status = status_s.get(0);
        }
        avaliador = new Avaliador();
        avaliador1 = new Avaliador();

        avaliador.setCargo(cargo);
        avaliador.setCodPessoa(pessoa.getCodUsuario());
        avaliador.setExperienciaEnsino(gerador.nextInt(10000));
        avaliador.setInstituicao(instituicao);
        avaliador.setSiapeAvaliador(String.valueOf(gerador.nextInt(10000)));
        avaliador.setStatusAvaliador(status);

        avaliador1.setCargo(cargo);
        avaliador1.setCodPessoa(pessoa.getCodUsuario());
        avaliador1.setExperienciaEnsino(gerador.nextInt(10000));
        avaliador1.setInstituicao(instituicao);
        avaliador1.setSiapeAvaliador(String.valueOf(gerador.nextInt(10000)));
        avaliador1.setStatusAvaliador(status);
    }

    @Test
    public void testInserir() {
        avaliadorController.create(avaliador);
        avaliadorController.create(avaliador);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        avaliadores = avaliadorController.findAvaliadorEntities();
        if (!avaliadores.isEmpty()) {
            avaliador = avaliadores.get(0);
        }
        // avaliador.setPercurso(String.valueOf(gerador.nextInt(10000)));
        avaliadorController.edit(avaliador);
    }

    @Test
    public void pesquisar() {
        avaliadores = avaliadorController.findAvaliadorEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        avaliadores = avaliadorController.findAvaliadorEntities();

        if (!avaliadores.isEmpty()) {
            avaliador = avaliadores.get(0);
        }
        avaliadorController.destroy(avaliador.getIdAvaliador());
    }
}
