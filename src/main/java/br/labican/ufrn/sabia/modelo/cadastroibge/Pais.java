package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="Pais")
@NamedQuery(name="Pais.findAll", query="SELECT p FROM Pais p")
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_pais")
	private Integer idPais;

	@Column(name="cod_ibge_pais")
	private Integer codIbgePais;

	@Column(name="nome_pais")
	private String nomePais;

	@Column(name="sigla_pais")
	private String siglaPais;

	public Pais() {
	}

	public Integer getIdPais() {
		return this.idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public Integer getCodIbgePais() {
		return this.codIbgePais;
	}

	public void setCodIbgePais(Integer codIbgePais) {
		this.codIbgePais = codIbgePais;
	}

	public String getNomePais() {
		return this.nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public String getSiglaPais() {
		return this.siglaPais;
	}

	public void setSiglaPais(String siglaPais) {
		this.siglaPais = siglaPais;
	}

}