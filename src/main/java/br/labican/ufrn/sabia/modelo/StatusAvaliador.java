package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the status_avaliador database table.
 *
 */
@Entity
@Table(name = "status_avaliador")
@NamedQuery(name = "StatusAvaliador.findAll", query = "SELECT s FROM StatusAvaliador s")
public class StatusAvaliador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_status_avaliador")
    private Integer idStatusAvaliador;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_status_avaliador")
    private Date dataStatusAvaliador;

    @Column(name = "justificativa_status_avaliador")
    private String justificativaStatusAvaliador;

    // bi-directional many-to-one association to Avaliador
    @OneToMany(mappedBy = "statusAvaliador")
    private List<Avaliador> avaliadors;

    // bi-directional many-to-one association to TipoStatusAvaliador
    @ManyToOne
    @JoinColumn(name = "cod_tipo_status_avaliador")
    private TipoStatusAvaliador tipoStatusAvaliador;

    public StatusAvaliador() {
    }

    public Integer getIdStatusAvaliador() {
        return this.idStatusAvaliador;
    }

    public void setIdStatusAvaliador(Integer idStatusAvaliador) {
        this.idStatusAvaliador = idStatusAvaliador;
    }

    public Date getDataStatusAvaliador() {
        return this.dataStatusAvaliador;
    }

    public void setDataStatusAvaliador(Date dataStatusAvaliador) {
        this.dataStatusAvaliador = dataStatusAvaliador;
    }

    public String getJustificativaStatusAvaliador() {
        return this.justificativaStatusAvaliador;
    }

    public void setJustificativaStatusAvaliador(
            String justificativaStatusAvaliador) {
        this.justificativaStatusAvaliador = justificativaStatusAvaliador;
    }

    public List<Avaliador> getAvaliadors() {
        return this.avaliadors;
    }

    public void setAvaliadors(List<Avaliador> avaliadors) {
        this.avaliadors = avaliadors;
    }

    public Avaliador addAvaliador(Avaliador avaliador) {
        getAvaliadors().add(avaliador);
        avaliador.setStatusAvaliador(this);

        return avaliador;
    }

    public Avaliador removeAvaliador(Avaliador avaliador) {
        getAvaliadors().remove(avaliador);
        avaliador.setStatusAvaliador(null);

        return avaliador;
    }

    public TipoStatusAvaliador getTipoStatusAvaliador() {
        return this.tipoStatusAvaliador;
    }

    public void setTipoStatusAvaliador(TipoStatusAvaliador tipoStatusAvaliador) {
        this.tipoStatusAvaliador = tipoStatusAvaliador;
    }

}