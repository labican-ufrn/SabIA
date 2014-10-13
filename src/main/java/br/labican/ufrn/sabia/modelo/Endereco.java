package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the endereco database table.
 *
 */
@Entity
@NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_endereco")
    private Integer idEndereco;

    private String bairro;

    private String complemento;

    private String logradoudo;

    @Column(name = "numero_endereco")
    private String numeroEndereco;

    @Column(name = "ponto_referencia")
    private String pontoReferencia;

    @Column(name = "tipo_endereco")
    private String tipoEndereco;

    // bi-directional many-to-one association to Instituicao
    @OneToMany(mappedBy = "endereco")
    private List<Instituicao> instituicaos;

    public Endereco() {
    }

    public Integer getIdEndereco() {
        return this.idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLogradoudo() {
        return this.logradoudo;
    }

    public void setLogradoudo(String logradoudo) {
        this.logradoudo = logradoudo;
    }

    public String getNumeroEndereco() {
        return this.numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getPontoReferencia() {
        return this.pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getTipoEndereco() {
        return this.tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public List<Instituicao> getInstituicaos() {
        return this.instituicaos;
    }

    public void setInstituicaos(List<Instituicao> instituicaos) {
        this.instituicaos = instituicaos;
    }

    public Instituicao addInstituicao(Instituicao instituicao) {
        getInstituicaos().add(instituicao);
        instituicao.setEndereco(this);

        return instituicao;
    }

    public Instituicao removeInstituicao(Instituicao instituicao) {
        getInstituicaos().remove(instituicao);
        instituicao.setEndereco(null);

        return instituicao;
    }

}