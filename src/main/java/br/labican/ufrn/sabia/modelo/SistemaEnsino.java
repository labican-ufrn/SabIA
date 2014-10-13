package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the sistema_ensino database table.
 *
 */
@Entity
@Table(name = "sistema_ensino")
@NamedQuery(name = "SistemaEnsino.findAll", query = "SELECT s FROM SistemaEnsino s")
public class SistemaEnsino implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sistema_ensino")
    private Integer idSistemaEnsino;

    @Column(name = "nome_sistema_ensino")
    private String nomeSistemaEnsino;

    // bi-directional many-to-one association to Instituicao
    @OneToMany(mappedBy = "sistemaEnsino")
    private List<Instituicao> instituicaos;

    public SistemaEnsino() {
    }

    public Integer getIdSistemaEnsino() {
        return this.idSistemaEnsino;
    }

    public void setIdSistemaEnsino(Integer idSistemaEnsino) {
        this.idSistemaEnsino = idSistemaEnsino;
    }

    public String getNomeSistemaEnsino() {
        return this.nomeSistemaEnsino;
    }

    public void setNomeSistemaEnsino(String nomeSistemaEnsino) {
        this.nomeSistemaEnsino = nomeSistemaEnsino;
    }

    public List<Instituicao> getInstituicaos() {
        return this.instituicaos;
    }

    public void setInstituicaos(List<Instituicao> instituicaos) {
        this.instituicaos = instituicaos;
    }

    public Instituicao addInstituicao(Instituicao instituicao) {
        getInstituicaos().add(instituicao);
        instituicao.setSistemaEnsino(this);

        return instituicao;
    }

    public Instituicao removeInstituicao(Instituicao instituicao) {
        getInstituicaos().remove(instituicao);
        instituicao.setSistemaEnsino(null);

        return instituicao;
    }

}