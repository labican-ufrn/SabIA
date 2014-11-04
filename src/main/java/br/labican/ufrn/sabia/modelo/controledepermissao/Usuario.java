package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="UsuarioSpring.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="cod_pessoa")
	private Integer codPessoa;

	private String login;

	private String senha;

	private String status;

	//bi-directional many-to-many association to Perfil
	@ManyToMany
	@JoinTable(
		name="usuario_perfil"
		, joinColumns={
			@JoinColumn(name="cod_usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="cod_perfil")
			}
		)
	private List<Perfil> perfils;

	public Usuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getCodPessoa() {
		return this.codPessoa;
	}

	public void setCodPessoa(Integer codPessoa) {
		this.codPessoa = codPessoa;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Perfil> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

}