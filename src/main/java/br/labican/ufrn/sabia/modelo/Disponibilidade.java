package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the disponibilidade database table.
 *
 */
@Entity
@NamedQuery(name = "Disponibilidade.findAll", query = "SELECT d FROM Disponibilidade d")
public class Disponibilidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_disponibilidade")
    private Integer idDisponibilidade;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_disponibilidade")
    private Date dataDisponibilidade;

    // bi-directional many-to-one association to SemanaAvaliacao
    @ManyToOne
    @JoinColumn(name = "cod_semana_avaliacao")
    private SemanaAvaliacao semanaAvaliacao;

    public Disponibilidade() {
    }

    public Integer getIdDisponibilidade() {
        return this.idDisponibilidade;
    }

    public void setIdDisponibilidade(Integer idDisponibilidade) {
        this.idDisponibilidade = idDisponibilidade;
    }

    public Date getDataDisponibilidade() {
        return this.dataDisponibilidade;
    }

    public void setDataDisponibilidade(Date dataDisponibilidade) {
        this.dataDisponibilidade = dataDisponibilidade;
    }

    public SemanaAvaliacao getSemanaAvaliacao() {
        return this.semanaAvaliacao;
    }

    public void setSemanaAvaliacao(SemanaAvaliacao semanaAvaliacao) {
        this.semanaAvaliacao = semanaAvaliacao;
    }

}