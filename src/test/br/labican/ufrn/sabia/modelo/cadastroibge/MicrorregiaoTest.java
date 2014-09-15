package br.labican.ufrn.sabia.testes;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.MesorregiaoJpaController;
import br.labican.ufrn.sabia.dao.MicrorregiaoJpaController;
import br.labican.ufrn.sabia.modelo.cadastroibge.Mesorregiao;
import br.labican.ufrn.sabia.modelo.cadastroibge.Microrregiao;
import br.labican.ufrn.sabia.util.Util;

public class MicrorregiaoTest {


	Microrregiao microrregiao;
	Mesorregiao mesorregiao; 
	MicrorregiaoJpaController microrregiaoController;
	MesorregiaoJpaController mesorregiaoController;
	
	@Before
    public void iniciar() throws Exception {
		microrregiaoController = new MicrorregiaoJpaController(Util.EMF);
		mesorregiaoController = new MesorregiaoJpaController(Util.EMF);
		
		mesorregiao = mesorregiaoController.findMesorregiao(20);
		microrregiao = new Microrregiao();
		
		microrregiao.setCodIbgeMicrorregiao(12);
		microrregiao.setMesorregiao(mesorregiao);
		microrregiao.setNomeMicrorregiao("nomeMicrorregiao");
	
	}	

	
	@Test
    public void testInserir() {
		microrregiaoController.create(microrregiao);
    }

}
