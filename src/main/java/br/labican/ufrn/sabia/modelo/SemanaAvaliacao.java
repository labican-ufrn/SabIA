package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the semana_avaliacao database table.
 *
 */
@Entity
@Table(name = "semana_avaliacao")
@NamedQuery(name = "SemanaAvaliacao.findAll", query = "SELECT s FROM SemanaAvaliacao s")
public class SemanaAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_semana_avaliacao")
    private Integer idSemanaAvaliacao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_semana_avalicao")
    private Date dataFimSemanaAvalicao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio_semana_avalicao")
    private Date dataInicioSemanaAvalicao;

    @Column(name = "numero_semana_avaliacao")
    private Integer numeroSemanaAvaliacao;

    // bi-directional many-to-one association to Avaliacao
    @OneToMany(mappedBy = "semanaAvaliacao")
    private List<Avaliacao> avaliacaos;

    // bi-directional many-to-one association to Disponibilidade
    @OneToMany(mappedBy = "semanaAvaliacao")
    private List<Disponibilidade> disponibilidades;

    public SemanaAvaliacao() {
    }

    public Integer getIdSemanaAvaliacao() {
        return this.idSemanaAvaliacao;
    }

    public void setIdSemanaAvaliacao(Integer idSemanaAvaliacao) {
        this.idSemanaAvaliacao = idSemanaAvaliacao;
    }

    public Date getDataFimSemanaAvalicao() {
        return this.dataFimSemanaAvalicao;
    }

    public void setDataFimSemanaAvalicao(Date dataFimSemanaAvalicao) {
        this.dataFimSemanaAvalicao = dataFimSemanaAvalicao;
    }

    public Date getDataInicioSemanaAvalicao() {
        return this.dataInicioSemanaAvalicao;
    }

    public void setDataInicioSemanaAvalicao(Date dataInicioSemanaAvalicao) {
        this.dataInicioSemanaAvalicao = dataInicioSemanaAvalicao;
    }

    public Integer getNumeroSemanaAvaliacao() {
        return this.numeroSemanaAvaliacao;
    }

    public void setNumeroSemanaAvaliacao(Integer numeroSemanaAvaliacao) {
        this.numeroSemanaAvaliacao = numeroSemanaAvaliacao;
    }

    public List<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(List<Avaliacao> avaliacaos) {
        this.avaliacaos = avaliacaos;
    }

    public Avaliacao addAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().add(avaliacao);
        avaliacao.setSemanaAvaliacao(this);

        return avaliacao;
    }

    public Avaliacao removeAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().remove(avaliacao);
        avaliacao.setSemanaAvaliacao(null);

        return avaliacao;
    }

    public List<Disponibilidade> getDisponibilidades() {
        return this.disponibilidades;
    }

    public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Disponibilidade addDisponibilidade(Disponibilidade disponibilidade) {
        getDisponibilidades().add(disponibilidade);
        disponibilidade.setSemanaAvaliacao(this);

        return disponibilidade;
    }

    public Disponibilidade removeDisponibilidade(Disponibilidade disponibilidade) {
        getDisponibilidades().remove(disponibilidade);
        disponibilidade.setSemanaAvaliacao(null);

        return disponibilidade;
    }

}