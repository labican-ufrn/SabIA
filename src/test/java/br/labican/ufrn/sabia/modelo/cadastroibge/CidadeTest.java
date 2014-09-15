package br.labican.ufrn.sabia.modelo.cadastroibge;


import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.CidadeJpaController;
import br.labican.ufrn.sabia.dao.MicrorregiaoJpaController;
import br.labican.ufrn.sabia.util.Util;

public class CidadeTest {

	Cidade cidade;
	Microrregiao microrregiao;
	CidadeJpaController cidadeController;
	MicrorregiaoJpaController microrregiaoController;
	
	@Before
    public void iniciar() throws Exception {
		cidadeController = new CidadeJpaController(Util.EMF);
		microrregiaoController = new MicrorregiaoJpaController(Util.EMF);	
		
		microrregiao = microrregiaoController.findMicrorregiao(21);
		cidade = new Cidade();
		
		
		cidade.setCodIbgeCidade(123);
		cidade.setNomeCidade("nomeCidade");
		cidade.setMicrorregiao(microrregiao);
	}	

	
	@Test
    public void testInserir() {
		cidadeController.create(cidade);
    }

}
