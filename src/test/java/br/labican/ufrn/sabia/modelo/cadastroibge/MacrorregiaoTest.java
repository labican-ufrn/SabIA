package br.labican.ufrn.sabia.modelo.cadastroibge;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.MacrorregiaoJpaController;
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

}
