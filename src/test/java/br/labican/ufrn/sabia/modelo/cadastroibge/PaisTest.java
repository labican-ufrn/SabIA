package br.labican.ufrn.sabia.modelo.cadastroibge;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.PaisJpaController;
import br.labican.ufrn.sabia.util.Util;

public class PaisTest {

	Pais pais;
	PaisJpaController paisController;
	
	@Before
    public void iniciar() throws Exception {
		paisController = new PaisJpaController(Util.EMF);
			
		pais = new Pais();
		pais.setCodIbgePais(123);
		pais.setNomePais("Brasil");
		pais.setSiglaPais("BR");
	}	

	
	@Test
    public void testInserir() {
		paisController.create(pais);
    }


}
