package br.labican.ufrn.sabia.testes;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EstadoJpaController;
import br.labican.ufrn.sabia.dao.MacrorregiaoJpaController;
import br.labican.ufrn.sabia.modelo.cadastroibge.Estado;
import br.labican.ufrn.sabia.modelo.cadastroibge.Macrorregiao;
import br.labican.ufrn.sabia.util.Util;

public class EstadoTest {

	Estado estado;
	Macrorregiao macrorregiao;
	
	MacrorregiaoJpaController macrorregiaoController;
	EstadoJpaController estadoController;
	
	@Before
    public void iniciar() throws Exception {
		estadoController = new EstadoJpaController(Util.EMF);
		macrorregiaoController = new MacrorregiaoJpaController(Util.EMF);
		
		macrorregiao = macrorregiaoController.findMacrorregiao(18);
		
		estado = new Estado();
		estado.setCodIbgeEstado(1234);
		estado.setMacrorregiao(macrorregiao);
		estado.setNomeEstado("Rio Grande do Norte");
		estado.setSiglaEstado("RN");		
	}	

	@Test
    public void testInserir() {
		estadoController.create(estado);
    }
}
