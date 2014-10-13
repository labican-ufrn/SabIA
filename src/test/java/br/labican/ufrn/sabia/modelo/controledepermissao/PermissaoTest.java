package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.controlepermissao.PermissaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;


public class PermissaoTest {

    Permissao permissao;
    Permissao permissao1;

    PermissaoJpaController permissaoController;

    List<Permissao> permissaos;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        permissaoController = new PermissaoJpaController(Util.EMF);

        permissao = new Permissao();
        permissao1 = new Permissao();

        permissao.setNomePermissao(String.valueOf(gerador.nextInt(10000)));

        permissao1.setNomePermissao(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        permissaoController.create(permissao);
        permissaoController.create(permissao1);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        permissaos = permissaoController.findPermissaoEntities();

        if (!permissaos.isEmpty()) {
            permissao = permissaos.get(0);
        }

        permissao.setNomePermissao(String.valueOf(gerador.nextInt(10000)));
        permissaoController.edit(permissao);
    }

    @Test
    public void pesquisar() {
        permissaos = permissaoController.findPermissaoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException, Exception {
        permissaos = permissaoController.findPermissaoEntities();
        if (!permissaos.isEmpty()) {
            permissao = permissaos.get(0);
        }
        permissaoController.destroy(permissao.getIdPermissao());
    }


}
