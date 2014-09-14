package br.labican.ufrn.sabia.testes;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.EstadoJpaController;
import br.labican.ufrn.sabia.dao.MesorregiaoJpaController;
import br.labican.ufrn.sabia.modelo.cadastroibge.Estado;
import br.labican.ufrn.sabia.modelo.cadastroibge.Mesorregiao;
import br.labican.ufrn.sabia.util.Util;

public class MesorregiaoTest {

	Mesorregiao mesorregiao;
    MesorregiaoJpaController mesorregiaoController;
	EstadoJpaController estadoController;
    Estado estado;
	
	@Before
    public void iniciar() throws Exception {
		mesorregiaoController = new MesorregiaoJpaController(Util.EMF);
		estadoController = new EstadoJpaController(Util.EMF);	
		
		mesorregiao = new Mesorregiao();
		estado = estadoController.findEstado(19);
		mesorregiao.setCodIbgeMesorregiao(12);
		mesorregiao.setEstado(estado);
		mesorregiao.setNomeMesorregiao("nomeMesorregiao");
		mesorregiao.setSiglaMesorregiao("SG");
	}	

	
	@Test
    public void testInserir() {
		mesorregiaoController.create(mesorregiao);
    }


}
