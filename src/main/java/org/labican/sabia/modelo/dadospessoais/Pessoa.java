package org.labican.sabia.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_primeiro_pessoa")
    private String nomePrimeiro;
    
    @Column(name = "nome_meio_pessoa")
    private String nomeMeio;
    
    @Basic(optional = false)
    @Column(name = "nome_ultimo_pessoa")
    private String nomeUltimo;
    
    @Column(name = "cpf")
    private String cpf;
    
    @Basic(optional = false)
    @Size(min = 7, max = 8)
    @Column(name = "sexo")
    private String sexo;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    
    @Column(name = "passaporte")
    private String passaporte;
    
    @Basic(optional = false)
    @Column(name = "curriculo_lattes")
    private String lattes;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "nacionalidade")
    private Pais nacionalidade; 
    
    //unidirecional
    @ManyToOne
    @JoinColumn(name = "naturalidade")
    private Municipio naturalidade;
    
    //unidirecional
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_endereco")
    private Endereco endereco;
    
    //unidirecional
    @OneToOne
    @JoinColumn(name = "cod_rg")
    private RG rg;
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_usuario")
    private Usuario usuario;
    
    //bidirecional
    @OneToMany(mappedBy = "pessoa")
    private List<ContatoPessoa> contatos;
    
    //bidirecional
    @OneToOne(mappedBy = "pessoa")
    private Avaliador avaliador;
    
    //construtores
    public Pessoa() {
    }

    public Pessoa(Integer id, String nomePrimeiro, String nomeMeio, String nomeUltimo, String cpf, String sexo, Date dataNascimento, String passaporte, String lattes, Pais nacionalidade, Municipio naturalidade, Endereco endereco, RG rg, Usuario usuario) {
        this.id = id;
        this.nomePrimeiro = nomePrimeiro;
        this.nomeMeio = nomeMeio;
        this.nomeUltimo = nomeUltimo;
        this.cpf = cpf;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.passaporte = passaporte;
        this.lattes = lattes;
        this.nacionalidade = nacionalidade;
        this.naturalidade = naturalidade;
        this.endereco = endereco;
        this.rg = rg;
        this.usuario = usuario;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNomePrimeiro() {
        return nomePrimeiro;
    }

    public String getNomeMeio() {
        return nomeMeio;
    }

    public String getNomeUltimo() {
        return nomeUltimo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public String getLattes() {
        return lattes;
    }

    public Pais getNacionalidade() {
        return nacionalidade;
    }

    public Municipio getNaturalidade() {
        return naturalidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public RG getRg() {
        return rg;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<ContatoPessoa> getContatos() {
        return contatos;
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }
    
    //sets
    public void setNomePrimeiro(String nomePrimeiro) {
        this.nomePrimeiro = nomePrimeiro;
    }

    public void setNomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
    }

    public void setNomeUltimo(String nomeUltimo) {
        this.nomeUltimo = nomeUltimo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }

    public void setNacionalidade(Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public void setNaturalidade(Municipio naturalidade) {
        this.naturalidade = naturalidade;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setRg(RG rg) {
        this.rg = rg;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }
    
    //metodos para o relacionamento bidirecional
    //contato
    public void addContato(ContatoPessoa contato) {
        contato.setPessoa(this);
        this.getContatos().add(contato);
    }
    
    public void removeContato(ContatoPessoa contato) {
        contato.setPessoa(null);
        this.getContatos().remove(contato);
    }
    
    //avaliador
    public void addAvaliador(Avaliador avaliador) {
        avaliador.setPessoa(this);
        this.setAvaliador(avaliador);
    }
    
    public void removeAvaliador(Avaliador avaliador) {
        avaliador.setPessoa(null);
        this.setAvaliador(null);
    }
    
    private final Pessoa getInstance() {
        return this;
    }
}
