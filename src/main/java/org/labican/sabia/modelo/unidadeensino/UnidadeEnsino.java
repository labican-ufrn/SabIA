package org.labican.sabia.modelo.unidadeensino;

import org.labican.sabia.modelo.localizacao.Endereco;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.labican.sabia.modelo.avaliacao.Avaliacao;
import org.labican.sabia.modelo.avaliador.Avaliador;
import org.labican.sabia.modelo.contato.ContatoUnidadeEnsino;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "unidade_ensino")
public class UnidadeEnsino implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidade_ensino")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_unidade_ensino")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "sigla")
    private String sigla;
    
    @Basic(optional = false)
    @Column(name = "cod_sistec_unidade_ensino")
    private int codigoSistec;
    
    @Basic(optional = false)
    @Column(name = "cod_inep_unidade_ensino")
    private int codigoInep;
    
    @Basic(optional = false)
    @Size(min = 6, max = 7)
    @Column(name = "categoria_administrativa")
    private String categoriaAdministrativa; //privada ou pública
    
    //unidirecional
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_rede_ensino")
    private RedeEnsino redeEnsino;
    
    //unidirecional
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_endereco")
    private Endereco endereco;
    
    //bidirecional
    @OneToMany(mappedBy = "unidadeEnsino")
    private List<ContatoUnidadeEnsino> contatos;
    
    //bidirecional
    @OneToMany(mappedBy = "unidadeEnsino")
    private List<Avaliador> avaliadores;
    
    //bidirecional
    @OneToMany(mappedBy = "unidadeEnsino")
    private List<Avaliacao> avaliacoes;
    
    //construtores
    public UnidadeEnsino() {
    }

    public UnidadeEnsino(String nome, String sigla, int codigoSistec, int codigoInep, String categoriaAdministrativa, RedeEnsino redeEnsino, Endereco endereco) {
        this.nome = nome;
        this.sigla = sigla;
        this.codigoSistec = codigoSistec;
        this.codigoInep = codigoInep;
        this.categoriaAdministrativa = categoriaAdministrativa;
        this.redeEnsino = redeEnsino;
        this.endereco = endereco;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public int getCodigoSistec() {
        return codigoSistec;
    }

    public int getCodigoInep() {
        return codigoInep;
    }

    public String getCategoriaAdministrativa() {
        return categoriaAdministrativa;
    }

    public RedeEnsino getRedeEnsino() {
        return redeEnsino;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public List<ContatoUnidadeEnsino> getContatos() {
        return contatos;
    }

    public List<Avaliador> getAvaliadores() {
        return avaliadores;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setCodigoSistec(int codigoSistec) {
        this.codigoSistec = codigoSistec;
    }

    public void setCodigoInep(int codigoInep) {
        this.codigoInep = codigoInep;
    }

    public void setCategoriaAdministrativa(String categoriaAdministrativa) {
        this.categoriaAdministrativa = categoriaAdministrativa;
    }

    public void setRedeEnsino(RedeEnsino redeEnsino) {
        this.redeEnsino = redeEnsino;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    //metodos para o relacionamento bidirecional
    //contato
    public void addContato(ContatoUnidadeEnsino contato) {
        contato.setUnidadeEnsino(this);
        this.getContatos().add(contato);
    }
    
    public void removeContato(ContatoUnidadeEnsino contato) {
        contato.setUnidadeEnsino(null);
        this.getContatos().remove(contato);
    }
    
    //avaliador
    public void addAvaliador(Avaliador avaliador) {
        avaliador.setUnidadeEnsino(this);
        this.getAvaliadores().add(avaliador);
    }
    
    public void removeAvaliador(Avaliador avaliador) {
        avaliador.setUnidadeEnsino(null);
        this.getAvaliadores().remove(avaliador);
    }
    
    //avaliacao
    public void addAvaliacao(Avaliacao avaliacao) {
        avaliacao.setUnidadeEnsino(this);
        this.getAvaliacoes().add(avaliacao);
    }
    
    public void removeAvaliacao(Avaliacao avaliacao) {
        avaliacao.setUnidadeEnsino(null);
        this.getAvaliacoes().remove(avaliacao);
    }
    
    private final UnidadeEnsino getInstance() {
        return this;
    }
}
