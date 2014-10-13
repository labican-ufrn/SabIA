package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the projeto database table.
 *
 */
@Entity
@NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p")
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_projeto")
    private Integer idProjeto;

    @Column(name = "nome_projeto")
    private String nomeProjeto;

    // bi-directional many-to-one association to Avaliacao
    @OneToMany(mappedBy = "projeto")
    private List<Avaliacao> avaliacaos;

    public Projeto() {
    }

    public Integer getIdProjeto() {
        return this.idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNomeProjeto() {
        return this.nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public List<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(List<Avaliacao> avaliacaos) {
        this.avaliacaos = avaliacaos;
    }

    public Avaliacao addAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().add(avaliacao);
        avaliacao.setProjeto(this);

        return avaliacao;
    }

    public Avaliacao removeAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().remove(avaliacao);
        avaliacao.setProjeto(null);

        return avaliacao;
    }

}
