package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.cadastroibge.EstadoJpaController;
import br.labican.ufrn.sabia.dao.dadospessoais.RgJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.modelo.cadastroibge.Estado;
import br.labican.ufrn.sabia.util.Util;

public class RgTest {

    Rg rg;
    Rg rg1;
    Rg rg2;
    Estado estado;

    EstadoJpaController estadoController;

    RgJpaController rgController;
    List<Rg> rgs;
    List<Estado> estados;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        rgController = new RgJpaController(Util.EMF);
        estadoController = new EstadoJpaController(Util.EMF);

        rg = new Rg();
        rg1 = new Rg();
        rg2 = new Rg();

        estados = estadoController.findEstadoEntities();

        if (!estados.isEmpty()) {
            estado = estados.get(0);
        }

        rg.setCodEstado(estado.getIdEstado());
        rg.setDataEmissao(new Date());
        rg.setNumeroRg(String.valueOf(gerador.nextInt(10000)));
        rg.setOrgaoEmissor(String.valueOf(gerador.nextInt(100)));

        rg1.setCodEstado(estado.getIdEstado());
        rg1.setDataEmissao(new Date());
        rg1.setNumeroRg(String.valueOf(gerador.nextInt(10000)));
        rg1.setOrgaoEmissor(String.valueOf(gerador.nextInt(100)));

        rg2.setCodEstado(estado.getIdEstado());
        rg2.setDataEmissao(new Date());
        rg2.setNumeroRg(String.valueOf(gerador.nextInt(10000)));
        rg2.setOrgaoEmissor(String.valueOf(gerador.nextInt(100)));
    }

    @Test
    public void testInserir() {
        rgController.create(rg);
        rgController.create(rg1);
        rgController.create(rg2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        rgs = rgController.findRgEntities();

        if (!rgs.isEmpty()) {
            rg = rgs.get(0);
        }

        rg.setNumeroRg("123456");
        rgController.edit(rg);
    }

    @Test
    public void pesquisar() {
        rgs = rgController.findRgEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException , Exception {
        rgs = rgController.findRgEntities();

        if (!rgs.isEmpty()) {
            rg = rgs.get(0);
            rgController.destroy(rg.getIdRg());
        }
    }
}
