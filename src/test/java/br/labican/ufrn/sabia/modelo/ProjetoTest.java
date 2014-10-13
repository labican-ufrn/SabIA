package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.ProjetoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class ProjetoTest {

    Projeto projeto;
    Projeto projeto2;

    ProjetoJpaController projetoController;
    List<Projeto> projetos;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        projetoController = new ProjetoJpaController(Util.EMF);

        projeto = new Projeto();
        projeto.setNomeProjeto(String.valueOf(gerador.nextInt(10000)));

        projeto2 = new Projeto();
        projeto2.setNomeProjeto(String.valueOf(gerador.nextInt(10000)));


    }

    @Test
    public void testInserir() {
        projetoController.create(projeto);
        projetoController.create(projeto2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        projetos = projetoController.findProjetoEntities();

        if (!projetos.isEmpty()) {
            projeto = projetos.get(0);
        }

        projeto.setNomeProjeto(String.valueOf(gerador.nextInt(10000)));
        projetoController.edit(projeto);
    }

    @Test
    public void pesquisar() {
        projetos = projetoController.findProjetoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        projetos = projetoController.findProjetoEntities();

        if (!projetos.isEmpty()) {
            projeto = projetos.get(0);
            projetoController.destroy(projeto.getIdProjeto());
        }
    }

}
