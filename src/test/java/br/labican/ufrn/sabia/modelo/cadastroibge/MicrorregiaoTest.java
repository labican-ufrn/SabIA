package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.MesorregiaoJpaController;
import br.labican.ufrn.sabia.dao.MicrorregiaoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class MicrorregiaoTest {


	Microrregiao microrregiao;
	Microrregiao microrregiao2;
	Mesorregiao mesorregiao; 
	MicrorregiaoJpaController microrregiaoController;
	MesorregiaoJpaController mesorregiaoController;
    List<Mesorregiao> mesos;
    List<Microrregiao> micros;
    Random gerador = new Random();

	@Before
    public void iniciar() throws Exception {
		microrregiaoController = new MicrorregiaoJpaController(Util.EMF);
		mesorregiaoController = new MesorregiaoJpaController(Util.EMF);
		
		mesos = mesorregiaoController.findMesorregiaoEntities();

		if (!mesos.isEmpty()) {
			mesorregiao = mesos.get(0);
		}
		
		microrregiao = new Microrregiao();
		
		microrregiao.setCodIbgeMicrorregiao(gerador.nextInt(10000));
		microrregiao.setMesorregiao(mesorregiao);
		microrregiao.setNomeMicrorregiao(String.valueOf(gerador.nextInt(10000)));

		microrregiao2 = new Microrregiao();
		
		microrregiao2.setCodIbgeMicrorregiao(gerador.nextInt(10000));
		microrregiao2.setMesorregiao(mesorregiao);
		microrregiao2.setNomeMicrorregiao(String.valueOf(gerador.nextInt(10000)));
	}	

	
	@Test
    public void testInserir() {
		microrregiaoController.create(microrregiao);
		microrregiaoController.create(microrregiao2);
    }
	
	@Test
	public void testEditar() throws NonexistentEntityException, Exception {
		micros = microrregiaoController.findMicrorregiaoEntities();

		if (!micros.isEmpty()) {
			microrregiao = micros.get(0);
		}

		microrregiao.setNomeMicrorregiao(String.valueOf(gerador.nextInt(10000)));
		microrregiaoController.edit(microrregiao);
	}

	@Test
	public void pesquisar() {
		micros = microrregiaoController.findMicrorregiaoEntities();

	}

	@Test
	public void excluir() throws NonexistentEntityException {
		micros = microrregiaoController.findMicrorregiaoEntities();

		if (!micros.isEmpty()) {
			microrregiao = micros.get(0);
			microrregiaoController.destroy(microrregiao.getIdMicrorregiao());
		}

		
	}
	
}
