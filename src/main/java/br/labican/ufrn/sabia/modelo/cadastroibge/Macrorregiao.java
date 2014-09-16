package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the macrorregiao database table.
 * 
 */
@Entity
@NamedQuery(name = "Macrorregiao.findAll", query = "SELECT m FROM Macrorregiao m")
public class Macrorregiao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_macrorregiao")
	private Integer idMacrorregiao;

	@Column(name = "cod_ibge_macrorregiao")
	private Integer codIbgeMacrorregiao;

	@Column(name = "nome_macrorregiao")
	private String nomeMacrorregiao;

	// bi-directional many-to-one association to Estado
	@OneToMany(mappedBy = "macrorregiao", cascade = CascadeType.ALL)
	private List<Estado> estados;

	public Macrorregiao() {
	}

	public Integer getIdMacrorregiao() {
		return this.idMacrorregiao;
	}

	public void setIdMacrorregiao(Integer idMacrorregiao) {
		this.idMacrorregiao = idMacrorregiao;
	}

	public Integer getCodIbgeMacrorregiao() {
		return this.codIbgeMacrorregiao;
	}

	public void setCodIbgeMacrorregiao(Integer codIbgeMacrorregiao) {
		this.codIbgeMacrorregiao = codIbgeMacrorregiao;
	}

	public String getNomeMacrorregiao() {
		return this.nomeMacrorregiao;
	}

	public void setNomeMacrorregiao(String nomeMacrorregiao) {
		this.nomeMacrorregiao = nomeMacrorregiao;
	}

	public List<Estado> getEstados() {
		return this.estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado addEstado(Estado estado) {
		getEstados().add(estado);
		estado.setMacrorregiao(this);

		return estado;
	}

	public Estado removeEstado(Estado estado) {
		getEstados().remove(estado);
		estado.setMacrorregiao(null);

		return estado;
	}

}