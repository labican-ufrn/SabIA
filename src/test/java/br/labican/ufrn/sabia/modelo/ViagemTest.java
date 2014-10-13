package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.ConfirmacaoAvaliadorJpaController;
import br.labican.ufrn.sabia.dao.ViagemJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class ViagemTest {

    Viagem viagem;
    Viagem viagem1;
    ConfirmacaoAvaliador confirmacaoAvaliador;

    ViagemJpaController viagemController;
    ConfirmacaoAvaliadorJpaController confirmacaoController;

    List<Viagem> viagens;
    List<ConfirmacaoAvaliador> confirmacoes;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        viagemController = new ViagemJpaController(Util.EMF);
        confirmacaoController = new ConfirmacaoAvaliadorJpaController(Util.EMF);

        confirmacoes = confirmacaoController.findConfirmacaoAvaliadorEntities();
        if(!confirmacoes.isEmpty()){
            confirmacaoAvaliador = confirmacoes.get(0);
        }

        viagem = new Viagem();
        viagem.setDataChegadaViagem(new Date());
        viagem.setDataSaidaViagem(new Date());
        viagem.setPercurso(String.valueOf(gerador.nextInt(10000)));
        viagem.setConfirmacaoAvaliador(confirmacaoAvaliador);
        viagem.setPercurso(String.valueOf(gerador.nextInt(10000)));

        viagem1 = new Viagem();
        viagem1.setDataChegadaViagem(new Date());
        viagem1.setDataSaidaViagem(new Date());
        viagem1.setPercurso(String.valueOf(gerador.nextInt(10000)));
        viagem1.setConfirmacaoAvaliador(confirmacaoAvaliador);
        viagem1.setPercurso(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        viagemController.create(viagem);
        viagemController.create(viagem);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        viagens = viagemController.findViagemEntities();

        if (!viagens.isEmpty()) {
            viagem = viagens.get(0);
        }

        viagem.setPercurso(String.valueOf(gerador.nextInt(10000)));
        viagemController.edit(viagem);
    }

    @Test
    public void pesquisar() {
        viagens = viagemController.findViagemEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        viagens = viagemController.findViagemEntities();

        if (!viagens.isEmpty()) {
            viagem = viagens.get(0);
        }
        viagemController.destroy(viagem.getIdViagem());
    }
}
