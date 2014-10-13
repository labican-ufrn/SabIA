package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.controlepermissao.PerfilJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class PerfilTest {

    Perfil perfil;
    Perfil perfil1;

    PerfilJpaController perfilController;

    List<Perfil> perfis;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        perfilController = new PerfilJpaController(Util.EMF);

        perfil = new Perfil();
        perfil1 = new Perfil();

        perfil.setNomePerfil(String.valueOf(gerador.nextInt(10000)));
        perfil1.setNomePerfil(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        perfilController.create(perfil);
        perfilController.create(perfil1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        perfis = perfilController.findPerfilEntities();

        if (!perfis.isEmpty()) {
            perfil = perfis.get(0);
        }

        perfil.setNomePerfil(String.valueOf(gerador.nextInt(10000)));
        perfilController.edit(perfil);
    }

    @Test
    public void pesquisar() {
        perfis = perfilController.findPerfilEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException , Exception {
        perfis = perfilController.findPerfilEntities();

        if (!perfis.isEmpty()) {
            perfil = perfis.get(0);
            perfilController.destroy(perfil.getIdPerfil());
        }
    }
}
