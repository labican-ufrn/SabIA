package br.labican.ufrn.sabia.modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.AtividadeJpaController;
import br.labican.ufrn.sabia.dao.ViagemJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class AtividadeTest {

    Atividade atividade;
    Atividade atividade2;
    Viagem viagem;

    AtividadeJpaController atividadeController;
    ViagemJpaController viagemController;

    List<Atividade> atividades;
    List<Viagem> viagens;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        viagemController = new ViagemJpaController(Util.EMF);
        atividadeController = new AtividadeJpaController(Util.EMF);
        viagens = viagemController.findViagemEntities();

        if (!viagens.isEmpty()) {
            viagem = viagens.get(0);
        }

        atividade = new Atividade();
        atividade.setDataAtividade(new Date());
        atividade.setDescricaoAtividade(String.valueOf(gerador.nextInt(10000)));
        atividade.setViagem(viagem);

        atividade2 = new Atividade();
        atividade2.setDataAtividade(new Date());
        atividade2.setDescricaoAtividade(String.valueOf(gerador.nextInt(10000)));
        atividade2.setViagem(viagem);

    }

    @Test
    public void testInserir() {
        atividadeController.create(atividade);
        atividadeController.create(atividade2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        atividades = atividadeController.findAtividadeEntities();

        if (!atividades.isEmpty()) {
            atividade = atividades.get(0);
        }

        atividade.setDescricaoAtividade(String.valueOf(gerador.nextInt(10000)));
        atividadeController.edit(atividade);
    }

    @Test
    public void pesquisar() {
        atividades = atividadeController.findAtividadeEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        atividades = atividadeController.findAtividadeEntities();

        if (!atividades.isEmpty()) {
            atividade = atividades.get(0);
            atividadeController.destroy(atividade.getIdAtividade());
        }
    }
}
