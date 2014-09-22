package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rg database table.
 * 
 */
@Entity
@NamedQuery(name="Rg.findAll", query="SELECT r FROM Rg r")
public class Rg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_rg")
	private Integer idRg;

	@Column(name="cod_estado")
	private Integer codEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="data_emissao")
	private Date dataEmissao;

	@Column(name="numero_rg")
	private String numeroRg;

	@Column(name="orgao_emissor")
	private String orgaoEmissor;

	//bi-directional many-to-one association to Pessoa
	@OneToMany(mappedBy="rg")
	private List<Pessoa> pessoas;

	public Rg() {
	}

	public Integer getIdRg() {
		return this.idRg;
	}

	public void setIdRg(Integer idRg) {
		this.idRg = idRg;
	}

	public Integer getCodEstado() {
		return this.codEstado;
	}

	public void setCodEstado(Integer codEstado) {
		this.codEstado = codEstado;
	}

	public Date getDataEmissao() {
		return this.dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getNumeroRg() {
		return this.numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getOrgaoEmissor() {
		return this.orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public List<Pessoa> getPessoas() {
		return this.pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa addPessoa(Pessoa pessoa) {
		getPessoas().add(pessoa);
		pessoa.setRg(this);

		return pessoa;
	}

	public Pessoa removePessoa(Pessoa pessoa) {
		getPessoas().remove(pessoa);
		pessoa.setRg(null);

		return pessoa;
	}

}