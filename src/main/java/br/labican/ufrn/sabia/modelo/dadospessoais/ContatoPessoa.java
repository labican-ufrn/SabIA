package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contato_pessoa database table.
 * 
 */
@Entity
@Table(name="contato_pessoa")
@NamedQuery(name="ContatoPessoa.findAll", query="SELECT c FROM ContatoPessoa c")
public class ContatoPessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_contato_pessoa")
	private Integer idContatoPessoa;

	@Column(name="valor_contato_pessoa")
	private String valorContatoPessoa;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	@JoinColumn(name="cod_pessoa")
	private Pessoa pessoa;

	//bi-directional many-to-one association to TipoContato
	@ManyToOne
	@JoinColumn(name="cod_tipo_contato")
	private TipoContato tipoContato;

	public ContatoPessoa() {
	}

	public Integer getIdContatoPessoa() {
		return this.idContatoPessoa;
	}

	public void setIdContatoPessoa(Integer idContatoPessoa) {
		this.idContatoPessoa = idContatoPessoa;
	}

	public String getValorContatoPessoa() {
		return this.valorContatoPessoa;
	}

	public void setValorContatoPessoa(String valorContatoPessoa) {
		this.valorContatoPessoa = valorContatoPessoa;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoContato getTipoContato() {
		return this.tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

}