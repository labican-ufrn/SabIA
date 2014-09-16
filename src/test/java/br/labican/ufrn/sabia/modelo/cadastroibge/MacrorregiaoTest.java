package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.MacrorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class MacrorregiaoTest {

	Macrorregiao macrorregiao;
	Macrorregiao macrorregiao2;
	MacrorregiaoJpaController macrorregiaoController;
	Random gerador = new Random();

	@Before
	public void iniciar() throws Exception {
		macrorregiaoController = new MacrorregiaoJpaController(Util.EMF);
		macrorregiao = new Macrorregiao();
		macrorregiao.setCodIbgeMacrorregiao(gerador.nextInt(10000));
		macrorregiao
				.setNomeMacrorregiao(String.valueOf(gerador.nextInt(10000)));

		macrorregiao2 = new Macrorregiao();
		macrorregiao2.setCodIbgeMacrorregiao(gerador.nextInt(10000));
		macrorregiao2
				.setNomeMacrorregiao(String.valueOf(gerador.nextInt(10000)));
	}

	@Test
	public void testInserir() {
		macrorregiaoController.create(macrorregiao);
		macrorregiaoController.create(macrorregiao2);
	}

	@Test
	public void testEditar() throws NonexistentEntityException, Exception {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();

		if (!macros.isEmpty()) {
			macrorregiao = macros.get(0);
		}

		macrorregiao
				.setNomeMacrorregiao(String.valueOf(gerador.nextInt(10000)));
		macrorregiao.setCodIbgeMacrorregiao(gerador.nextInt(10000));
		macrorregiaoController.edit(macrorregiao);
	}

	@Test
	public void pesquisar() {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();
	}

	@Test
	public void excluir() throws NonexistentEntityException {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();

		if (!macros.isEmpty()) {
			macrorregiao = macros.get(0);
			macrorregiaoController.destroy(macrorregiao.getIdMacrorregiao());
		}
	}
}
