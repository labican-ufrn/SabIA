package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cidade database table.
 * 
 */
@Entity
@NamedQuery(name="Cidade.findAll", query="SELECT c FROM Cidade c")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_cidade")
	private Integer idCidade = 0;

	@Column(name="cod_ibge_cidade")
	private Integer codIbgeCidade;

	@Column(name="nome_cidade")
	private String nomeCidade;

	//bi-directional many-to-one association to Microrregiao
	@ManyToOne
	@JoinColumn(name="cod_microrregiao")
	private Microrregiao microrregiao;

	public Cidade() {
	}

	public Integer getIdCidade() {
		return this.idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public Integer getCodIbgeCidade() {
		return this.codIbgeCidade;
	}

	public void setCodIbgeCidade(Integer codIbgeCidade) {
		this.codIbgeCidade = codIbgeCidade;
	}

	public String getNomeCidade() {
		return this.nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public Microrregiao getMicrorregiao() {
		return this.microrregiao;
	}

	public void setMicrorregiao(Microrregiao microrregiao) {
		this.microrregiao = microrregiao;
	}

}