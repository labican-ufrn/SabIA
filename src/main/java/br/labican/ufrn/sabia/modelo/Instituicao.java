package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the instituicao database table.
 *
 */
@Entity
@NamedQuery(name = "Instituicao.findAll", query = "SELECT i FROM Instituicao i")
public class Instituicao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_instituicao")
    private Integer idInstituicao;

    @Column(name = "cod_inep_instituicao")
    private Integer codInepInstituicao;

    @Column(name = "cod_sistec_instituicao")
    private Integer codSistecInstituicao;

    @Column(name = "nome_instituicao")
    private String nomeInstituicao;

    // bi-directional many-to-one association to Avaliacao
    @OneToMany(mappedBy = "instituicao")
    private List<Avaliacao> avaliacaos;

    // bi-directional many-to-one association to Avaliador
    @OneToMany(mappedBy = "instituicao")
    private List<Avaliador> avaliadors;

    // bi-directional many-to-one association to ContatoInstituicao
    @OneToMany(mappedBy = "instituicao")
    private List<ContatoInstituicao> contatoInstituicaos;

    // bi-directional many-to-one association to Endereco
    @ManyToOne
    @JoinColumn(name = "cod_endereco")
    private Endereco endereco;

    // bi-directional many-to-one association to SistemaEnsino
    @ManyToOne
    @JoinColumn(name = "cod_sistema_ensino")
    private SistemaEnsino sistemaEnsino;

    // bi-directional many-to-one association to TipoInstituicao
    @ManyToOne
    @JoinColumn(name = "cod_tipo_instituicao")
    private TipoInstituicao tipoInstituicao;

    public Instituicao() {
    }

    public Integer getIdInstituicao() {
        return this.idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public Integer getCodInepInstituicao() {
        return this.codInepInstituicao;
    }

    public void setCodInepInstituicao(Integer codInepInstituicao) {
        this.codInepInstituicao = codInepInstituicao;
    }

    public Integer getCodSistecInstituicao() {
        return this.codSistecInstituicao;
    }

    public void setCodSistecInstituicao(Integer codSistecInstituicao) {
        this.codSistecInstituicao = codSistecInstituicao;
    }

    public String getNomeInstituicao() {
        return this.nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public List<Avaliacao> getAvaliacaos() {
        return this.avaliacaos;
    }

    public void setAvaliacaos(List<Avaliacao> avaliacaos) {
        this.avaliacaos = avaliacaos;
    }

    public Avaliacao addAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().add(avaliacao);
        avaliacao.setInstituicao(this);

        return avaliacao;
    }

    public Avaliacao removeAvaliacao(Avaliacao avaliacao) {
        getAvaliacaos().remove(avaliacao);
        avaliacao.setInstituicao(null);

        return avaliacao;
    }

    public List<Avaliador> getAvaliadors() {
        return this.avaliadors;
    }

    public void setAvaliadors(List<Avaliador> avaliadors) {
        this.avaliadors = avaliadors;
    }

    public Avaliador addAvaliador(Avaliador avaliador) {
        getAvaliadors().add(avaliador);
        avaliador.setInstituicao(this);

        return avaliador;
    }

    public Avaliador removeAvaliador(Avaliador avaliador) {
        getAvaliadors().remove(avaliador);
        avaliador.setInstituicao(null);

        return avaliador;
    }

    public List<ContatoInstituicao> getContatoInstituicaos() {
        return this.contatoInstituicaos;
    }

    public void setContatoInstituicaos(
            List<ContatoInstituicao> contatoInstituicaos) {
        this.contatoInstituicaos = contatoInstituicaos;
    }

    public ContatoInstituicao addContatoInstituicao(
            ContatoInstituicao contatoInstituicao) {
        getContatoInstituicaos().add(contatoInstituicao);
        contatoInstituicao.setInstituicao(this);

        return contatoInstituicao;
    }

    public ContatoInstituicao removeContatoInstituicao(
            ContatoInstituicao contatoInstituicao) {
        getContatoInstituicaos().remove(contatoInstituicao);
        contatoInstituicao.setInstituicao(null);

        return contatoInstituicao;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public SistemaEnsino getSistemaEnsino() {
        return this.sistemaEnsino;
    }

    public void setSistemaEnsino(SistemaEnsino sistemaEnsino) {
        this.sistemaEnsino = sistemaEnsino;
    }

    public TipoInstituicao getTipoInstituicao() {
        return this.tipoInstituicao;
    }

    public void setTipoInstituicao(TipoInstituicao tipoInstituicao) {
        this.tipoInstituicao = tipoInstituicao;
    }

}