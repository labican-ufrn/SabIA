package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.dadospessoais.TipoContatoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class TipoContatoTest {

    TipoContato tc;
    TipoContato tc1;

    TipoContatoJpaController tcController;

    List<TipoContato> tcs;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        tcController = new TipoContatoJpaController(Util.EMF);

        tc = new TipoContato();
        tc1 = new TipoContato();

        tcs = tcController.findTipoContatoEntities();

        tc.setExpressaoRegular(String.valueOf(gerador.nextInt(10000)));
        tc.setNomeTipoContato(String.valueOf(gerador.nextInt(10000)));

        tc1.setExpressaoRegular(String.valueOf(gerador.nextInt(10000)));
        tc1.setNomeTipoContato(String.valueOf(gerador.nextInt(10000)));

    }

    @Test
    public void testInserir() {
        tcController.create(tc);
        tcController.create(tc1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        tcs = tcController.findTipoContatoEntities();

        if (!tcs.isEmpty()) {
            tc = tcs.get(0);
        }

        tc.setExpressaoRegular(String.valueOf(gerador.nextInt(10000)));
        tcController.edit(tc);
    }

    @Test
    public void pesquisar() {
        tcs = tcController.findTipoContatoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        tcs = tcController.findTipoContatoEntities();

        if (!tcs.isEmpty()) {
            tc = tcs.get(0);
            tcController.destroy(tc.getIdTipoContato());
        }
    }
}
