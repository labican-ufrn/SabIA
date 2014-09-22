package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pessoa database table.
 * 
 */
@Entity
@NamedQuery(name="Pessoa.findAll", query="SELECT p FROM Pessoa p")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_pessoa")
	private Integer idPessoa;

	@Column(name="cod_endereco")
	private Integer codEndereco;

	@Column(name="cod_usuario")
	private Integer codUsuario;

	private String cpf;

	@Column(name="curriculo_lattes")
	private String curriculoLattes;

	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento")
	private Date dataNascimento;

	private Integer nacionalidade;

	private Integer naturalidade;

	@Column(name="nome_meio_pessoa")
	private String nomeMeioPessoa;

	@Column(name="nome_primeiro_pessoa")
	private String nomePrimeiroPessoa;

	@Column(name="nome_ultimo_pessoa")
	private String nomeUltimoPessoa;

	private String passaporte;

	private String sexo;

	//bi-directional many-to-one association to ContatoPessoa
	@OneToMany(mappedBy="pessoa")
	private List<ContatoPessoa> contatoPessoas;

	//bi-directional many-to-one association to Rg
	@ManyToOne
	@JoinColumn(name="cod_rg")
	private Rg rg;

	public Pessoa() {
	}

	public Integer getIdPessoa() {
		return this.idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Integer getCodEndereco() {
		return this.codEndereco;
	}

	public void setCodEndereco(Integer codEndereco) {
		this.codEndereco = codEndereco;
	}

	public Integer getCodUsuario() {
		return this.codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCurriculoLattes() {
		return this.curriculoLattes;
	}

	public void setCurriculoLattes(String curriculoLattes) {
		this.curriculoLattes = curriculoLattes;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getNacionalidade() {
		return this.nacionalidade;
	}

	public void setNacionalidade(Integer nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Integer getNaturalidade() {
		return this.naturalidade;
	}

	public void setNaturalidade(Integer naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNomeMeioPessoa() {
		return this.nomeMeioPessoa;
	}

	public void setNomeMeioPessoa(String nomeMeioPessoa) {
		this.nomeMeioPessoa = nomeMeioPessoa;
	}

	public String getNomePrimeiroPessoa() {
		return this.nomePrimeiroPessoa;
	}

	public void setNomePrimeiroPessoa(String nomePrimeiroPessoa) {
		this.nomePrimeiroPessoa = nomePrimeiroPessoa;
	}

	public String getNomeUltimoPessoa() {
		return this.nomeUltimoPessoa;
	}

	public void setNomeUltimoPessoa(String nomeUltimoPessoa) {
		this.nomeUltimoPessoa = nomeUltimoPessoa;
	}

	public String getPassaporte() {
		return this.passaporte;
	}

	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<ContatoPessoa> getContatoPessoas() {
		return this.contatoPessoas;
	}

	public void setContatoPessoas(List<ContatoPessoa> contatoPessoas) {
		this.contatoPessoas = contatoPessoas;
	}

	public ContatoPessoa addContatoPessoa(ContatoPessoa contatoPessoa) {
		getContatoPessoas().add(contatoPessoa);
		contatoPessoa.setPessoa(this);

		return contatoPessoa;
	}

	public ContatoPessoa removeContatoPessoa(ContatoPessoa contatoPessoa) {
		getContatoPessoas().remove(contatoPessoa);
		contatoPessoa.setPessoa(null);

		return contatoPessoa;
	}

	public Rg getRg() {
		return this.rg;
	}

	public void setRg(Rg rg) {
		this.rg = rg;
	}

}