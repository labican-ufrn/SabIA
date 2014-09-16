package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the microrregiao database table.
 * 
 */
@Entity
@NamedQuery(name="Microrregiao.findAll", query="SELECT m FROM Microrregiao m")
public class Microrregiao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_microrregiao")
	private Integer idMicrorregiao;

	@Column(name="cod_ibge_microrregiao")
	private Integer codIbgeMicrorregiao;

	@Column(name="nome_microrregiao")
	private String nomeMicrorregiao;

	//bi-directional many-to-one association to Cidade
	@OneToMany(mappedBy="microrregiao", cascade = CascadeType.ALL)
	private List<Cidade> cidades;

	//bi-directional many-to-one association to Mesorregiao
	@ManyToOne
	@JoinColumn(name="cod_mesorregiao")
	private Mesorregiao mesorregiao;

	public Microrregiao() {
	}

	public Integer getIdMicrorregiao() {
		return this.idMicrorregiao;
	}

	public void setIdMicrorregiao(Integer idMicrorregiao) {
		this.idMicrorregiao = idMicrorregiao;
	}

	public Integer getCodIbgeMicrorregiao() {
		return this.codIbgeMicrorregiao;
	}

	public void setCodIbgeMicrorregiao(Integer codIbgeMicrorregiao) {
		this.codIbgeMicrorregiao = codIbgeMicrorregiao;
	}

	public String getNomeMicrorregiao() {
		return this.nomeMicrorregiao;
	}

	public void setNomeMicrorregiao(String nomeMicrorregiao) {
		this.nomeMicrorregiao = nomeMicrorregiao;
	}

	public List<Cidade> getCidades() {
		return this.cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Cidade addCidade(Cidade cidade) {
		getCidades().add(cidade);
		cidade.setMicrorregiao(this);

		return cidade;
	}

	public Cidade removeCidade(Cidade cidade) {
		getCidades().remove(cidade);
		cidade.setMicrorregiao(null);

		return cidade;
	}

	public Mesorregiao getMesorregiao() {
		return this.mesorregiao;
	}

	public void setMesorregiao(Mesorregiao mesorregiao) {
		this.mesorregiao = mesorregiao;
	}

}