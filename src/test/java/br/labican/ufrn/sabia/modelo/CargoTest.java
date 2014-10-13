package br.labican.ufrn.sabia.modelo;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.labican.ufrn.sabia.dao.CargoJpaController;
import br.labican.ufrn.sabia.dao.exceptions.NonexistentEntityException;
import br.labican.ufrn.sabia.util.Util;

public class CargoTest {

    Cargo cargo;
    Cargo cargo2;

    CargoJpaController cargoController;
    List<Cargo> cargos;

    Random gerador = new Random();

    @Before
    public void iniciar() throws Exception {
        cargoController = new CargoJpaController(Util.EMF);

        cargo = new Cargo();
        cargo.setNomeCargo(String.valueOf(gerador.nextInt(10000)));

        cargo2 = new Cargo();
        cargo2.setNomeCargo(String.valueOf(gerador.nextInt(10000)));
    }

    @Test
    public void testInserir() {
        cargoController.create(cargo);
        cargoController.create(cargo2);
    }

    @Test
    public void testEditar() throws NonexistentEntityException, Exception {
        cargos = cargoController.findCargoEntities();

        if (!cargos.isEmpty()) {
            cargo = cargos.get(0);
        }

        cargo.setNomeCargo(String.valueOf(gerador.nextInt(10000)));
        cargoController.edit(cargo);
    }

    @Test
    public void pesquisar() {
        cargos = cargoController.findCargoEntities();
    }

    @Test
    public void excluir() throws NonexistentEntityException {
        cargos = cargoController.findCargoEntities();

        if (!cargos.isEmpty()) {
            cargo = cargos.get(0);
            cargoController.destroy(cargo.getIdCargo());
        }
    }


}
