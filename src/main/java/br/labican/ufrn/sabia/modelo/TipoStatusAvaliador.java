package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the tipo_status_avaliador database table.
 *
 */
@Entity
@Table(name = "tipo_status_avaliador")
@NamedQuery(name = "TipoStatusAvaliador.findAll", query = "SELECT t FROM TipoStatusAvaliador t")
public class TipoStatusAvaliador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tipo_status_avaliador")
    private Integer idTipoStatusAvaliador;

    @Column(name = "nome_tipo_status_avaliador")
    private String nomeTipoStatusAvaliador;

    // bi-directional many-to-one association to StatusAvaliador
    @OneToMany(mappedBy = "tipoStatusAvaliador")
    private List<StatusAvaliador> statusAvaliadors;

    public TipoStatusAvaliador() {
    }

    public Integer getIdTipoStatusAvaliador() {
        return this.idTipoStatusAvaliador;
    }

    public void setIdTipoStatusAvaliador(Integer idTipoStatusAvaliador) {
        this.idTipoStatusAvaliador = idTipoStatusAvaliador;
    }

    public String getNomeTipoStatusAvaliador() {
        return this.nomeTipoStatusAvaliador;
    }

    public void setNomeTipoStatusAvaliador(String nomeTipoStatusAvaliador) {
        this.nomeTipoStatusAvaliador = nomeTipoStatusAvaliador;
    }

    public List<StatusAvaliador> getStatusAvaliadors() {
        return this.statusAvaliadors;
    }

    public void setStatusAvaliadors(List<StatusAvaliador> statusAvaliadors) {
        this.statusAvaliadors = statusAvaliadors;
    }

    public StatusAvaliador addStatusAvaliador(StatusAvaliador statusAvaliador) {
        getStatusAvaliadors().add(statusAvaliador);
        statusAvaliador.setTipoStatusAvaliador(this);

        return statusAvaliador;
    }

    public StatusAvaliador removeStatusAvaliador(StatusAvaliador statusAvaliador) {
        getStatusAvaliadors().remove(statusAvaliador);
        statusAvaliador.setTipoStatusAvaliador(null);

        return statusAvaliador;
    }

}