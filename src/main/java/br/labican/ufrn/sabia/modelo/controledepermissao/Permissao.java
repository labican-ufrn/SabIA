package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the permissao database table.
 * 
 */
@Entity
@NamedQuery(name="Permissao.findAll", query="SELECT p FROM Permissao p")
public class Permissao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_permissao")
	private Integer idPermissao;

	@Column(name="nome_permissao")
	private String nomePermissao;

	//bi-directional many-to-many association to Perfil
	@ManyToMany(mappedBy="permissaos")
	private List<Perfil> perfils;

	public Permissao() {
	}

	public Integer getIdPermissao() {
		return this.idPermissao;
	}

	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	public String getNomePermissao() {
		return this.nomePermissao;
	}

	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

	public List<Perfil> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

}