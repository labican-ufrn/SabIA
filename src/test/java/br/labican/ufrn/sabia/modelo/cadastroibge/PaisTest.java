package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.PaisJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class PaisTest {

	Pais pais;
	Pais pais2;
	PaisJpaController paisController;
	Random gerador = new Random();
	List<Pais> paises;
	
	@Before
    public void iniciar() throws Exception {
		paisController = new PaisJpaController(Util.EMF);
			
		pais = new Pais();
		pais.setCodIbgePais(gerador.nextInt(10000));
		pais.setNomePais(String.valueOf(gerador.nextInt(10000)));
		pais.setSiglaPais(String.valueOf(gerador.nextInt(10000)));
		
		pais2 = new Pais();
		pais2.setCodIbgePais(gerador.nextInt(10000));
		pais2.setNomePais(String.valueOf(gerador.nextInt(10000)));
		pais2.setSiglaPais(String.valueOf(gerador.nextInt(10000)));
	}	

	
	@Test
    public void testInserir() {
		paisController.create(pais);
		paisController.create(pais2);
    }

	@Test
	public void testEditar() throws NonexistentEntityException, Exception {
		paises = paisController.findPaisEntities();

		if (!paises.isEmpty()) {
			pais = paises.get(0);
		}

		pais.setNomePais(String.valueOf(gerador.nextInt(10000)));
		paisController.edit(pais);
	}

	@Test
	public void pesquisar() {
		paises = paisController.findPaisEntities();
	}

	@Test
	public void excluir() throws NonexistentEntityException {
		paises = paisController.findPaisEntities();

		if (!paises.isEmpty()) {
			pais = paises.get(0);
			paisController.destroy(pais.getIdPais());
		}

	}
}
