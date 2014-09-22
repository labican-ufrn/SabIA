package br.labican.ufrn.sabia.modelo.dadospessoais;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_contato database table.
 * 
 */
@Entity
@Table(name="tipo_contato")
@NamedQuery(name="TipoContato.findAll", query="SELECT t FROM TipoContato t")
public class TipoContato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_tipo_contato")
	private Integer idTipoContato;

	@Column(name="expressao_regular")
	private String expressaoRegular;

	@Column(name="nome_tipo_contato")
	private String nomeTipoContato;

	//bi-directional many-to-one association to ContatoPessoa
	@OneToMany(mappedBy="tipoContato")
	private List<ContatoPessoa> contatoPessoas;

	public TipoContato() {
	}

	public Integer getIdTipoContato() {
		return this.idTipoContato;
	}

	public void setIdTipoContato(Integer idTipoContato) {
		this.idTipoContato = idTipoContato;
	}

	public String getExpressaoRegular() {
		return this.expressaoRegular;
	}

	public void setExpressaoRegular(String expressaoRegular) {
		this.expressaoRegular = expressaoRegular;
	}

	public String getNomeTipoContato() {
		return this.nomeTipoContato;
	}

	public void setNomeTipoContato(String nomeTipoContato) {
		this.nomeTipoContato = nomeTipoContato;
	}

	public List<ContatoPessoa> getContatoPessoas() {
		return this.contatoPessoas;
	}

	public void setContatoPessoas(List<ContatoPessoa> contatoPessoas) {
		this.contatoPessoas = contatoPessoas;
	}

	public ContatoPessoa addContatoPessoa(ContatoPessoa contatoPessoa) {
		getContatoPessoas().add(contatoPessoa);
		contatoPessoa.setTipoContato(this);

		return contatoPessoa;
	}

	public ContatoPessoa removeContatoPessoa(ContatoPessoa contatoPessoa) {
		getContatoPessoas().remove(contatoPessoa);
		contatoPessoa.setTipoContato(null);

		return contatoPessoa;
	}

}