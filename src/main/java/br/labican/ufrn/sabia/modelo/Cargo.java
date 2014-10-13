package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the cargo database table.
 *
 */
@Entity
@NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "nome_cargo")
    private String nomeCargo;

    // bi-directional many-to-one association to Avaliador
    @OneToMany(mappedBy = "cargo")
    private List<Avaliador> avaliadors;

    public Cargo() {
    }

    public Integer getIdCargo() {
        return this.idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNomeCargo() {
        return this.nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public List<Avaliador> getAvaliadors() {
        return this.avaliadors;
    }

    public void setAvaliadors(List<Avaliador> avaliadors) {
        this.avaliadors = avaliadors;
    }

    public Avaliador addAvaliador(Avaliador avaliador) {
        getAvaliadors().add(avaliador);
        avaliador.setCargo(this);

        return avaliador;
    }

    public Avaliador removeAvaliador(Avaliador avaliador) {
        getAvaliadors().remove(avaliador);
        avaliador.setCargo(null);

        return avaliador;
    }

}