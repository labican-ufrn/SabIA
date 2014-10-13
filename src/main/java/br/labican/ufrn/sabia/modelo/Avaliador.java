package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the avaliador database table.
 *
 */
@Entity
@NamedQuery(name = "Avaliador.findAll", query = "SELECT a FROM Avaliador a")
public class Avaliador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_avaliador")
    private Integer idAvaliador;

    @Column(name = "cod_pessoa")
    private Integer codPessoa;

    @Column(name = "experiencia_ensino")
    private Integer experienciaEnsino;

    @Column(name = "siape_avaliador")
    private String siapeAvaliador;

    // bi-directional many-to-one association to Cargo
    @ManyToOne
    @JoinColumn(name = "cod_cargo")
    private Cargo cargo;

    // bi-directional many-to-one association to Instituicao
    @ManyToOne
    @JoinColumn(name = "cod_instituicao")
    private Instituicao instituicao;

    // bi-directional many-to-one association to StatusAvaliador
    @ManyToOne
    @JoinColumn(name = "cod_status_avaliador")
    private StatusAvaliador statusAvaliador;

    // bi-directional many-to-many association to EixoTecnologico
    @ManyToMany(mappedBy = "avaliadors")
    private List<EixoTecnologico> eixoTecnologicos;

    // bi-directional many-to-one association to ConfirmacaoAvaliador
    @OneToMany(mappedBy = "avaliador")
    private List<ConfirmacaoAvaliador> confirmacaoAvaliadors;

    // bi-directional many-to-many association to Equipe
    @ManyToMany(mappedBy = "avaliadors")
    private List<Equipe> equipes;

    public Avaliador() {
    }

    public Integer getIdAvaliador() {
        return this.idAvaliador;
    }

    public void setIdAvaliador(Integer idAvaliador) {
        this.idAvaliador = idAvaliador;
    }

    public Integer getCodPessoa() {
        return this.codPessoa;
    }

    public void setCodPessoa(Integer codPessoa) {
        this.codPessoa = codPessoa;
    }

    public Integer getExperienciaEnsino() {
        return this.experienciaEnsino;
    }

    public void setExperienciaEnsino(Integer experienciaEnsino) {
        this.experienciaEnsino = experienciaEnsino;
    }

    public String getSiapeAvaliador() {
        return this.siapeAvaliador;
    }

    public void setSiapeAvaliador(String siapeAvaliador) {
        this.siapeAvaliador = siapeAvaliador;
    }

    public Cargo getCargo() {
        return this.cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public StatusAvaliador getStatusAvaliador() {
        return this.statusAvaliador;
    }

    public void setStatusAvaliador(StatusAvaliador statusAvaliador) {
        this.statusAvaliador = statusAvaliador;
    }

    public List<EixoTecnologico> getEixoTecnologicos() {
        return this.eixoTecnologicos;
    }

    public void setEixoTecnologicos(List<EixoTecnologico> eixoTecnologicos) {
        this.eixoTecnologicos = eixoTecnologicos;
    }

    public List<ConfirmacaoAvaliador> getConfirmacaoAvaliadors() {
        return this.confirmacaoAvaliadors;
    }

    public void setConfirmacaoAvaliadors(
            List<ConfirmacaoAvaliador> confirmacaoAvaliadors) {
        this.confirmacaoAvaliadors = confirmacaoAvaliadors;
    }

    public ConfirmacaoAvaliador addConfirmacaoAvaliador(
            ConfirmacaoAvaliador confirmacaoAvaliador) {
        getConfirmacaoAvaliadors().add(confirmacaoAvaliador);
        confirmacaoAvaliador.setAvaliador(this);

        return confirmacaoAvaliador;
    }

    public ConfirmacaoAvaliador removeConfirmacaoAvaliador(
            ConfirmacaoAvaliador confirmacaoAvaliador) {
        getConfirmacaoAvaliadors().remove(confirmacaoAvaliador);
        confirmacaoAvaliador.setAvaliador(null);

        return confirmacaoAvaliador;
    }

    public List<Equipe> getEquipes() {
        return this.equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

}