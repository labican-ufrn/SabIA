package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.MacrorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class MacrorregiaoTest {

	Macrorregiao macrorregiao;
	MacrorregiaoJpaController macrorregiaoController;

	@Before
	public void iniciar() throws Exception {
		macrorregiaoController = new MacrorregiaoJpaController(Util.EMF);
		macrorregiao = new Macrorregiao();
		macrorregiao.setCodIbgeMacrorregiao(12345);
		macrorregiao.setNomeMacrorregiao("nomeMacrorregiao");
	}

	@Test
	public void testInserir() {
		macrorregiaoController.create(macrorregiao);
	}

	@Test
	public void testEditar() throws NonexistentEntityException, Exception {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();

		if (!macros.isEmpty()) {
			macrorregiao = macros.get(0);
		}

		macrorregiao.setNomeMacrorregiao("macro 2");
		macrorregiao.setCodIbgeMacrorregiao(54321);
		macrorregiaoController.edit(macrorregiao);
	}

	@Test
	public void pesquisar() {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();
		Assert.assertNotNull(macros);
	}

	@Test
	public void excluir() throws NonexistentEntityException {
		List<Macrorregiao> macros = macrorregiaoController
				.findMacrorregiaoEntities();

		if (!macros.isEmpty()) {
			macrorregiao = macros.get(0);
			macrorregiaoController.destroy(macrorregiao
					.getCodIbgeMacrorregiao());
		}
	}
}
