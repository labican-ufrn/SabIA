package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the titulacao database table.
 *
 */
@Entity
@NamedQuery(name = "Titulacao.findAll", query = "SELECT t FROM Titulacao t")
public class Titulacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_titulacao")
    private Integer idTitulacao;

    @Column(name = "nome_titulacao")
    private String nomeTitulacao;

    public Titulacao() {
    }

    public Integer getIdTitulacao() {
        return this.idTitulacao;
    }

    public void setIdTitulacao(Integer idTitulacao) {
        this.idTitulacao = idTitulacao;
    }

    public String getNomeTitulacao() {
        return this.nomeTitulacao;
    }

    public void setNomeTitulacao(String nomeTitulacao) {
        this.nomeTitulacao = nomeTitulacao;
    }

}