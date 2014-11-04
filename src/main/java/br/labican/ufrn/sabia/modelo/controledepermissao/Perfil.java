package br.labican.ufrn.sabia.modelo.controledepermissao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the perfil database table.
 * 
 */
@Entity
@NamedQuery(name="Perfil.findAll", query="SELECT p FROM Perfil p")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_perfil")
	private Integer idPerfil;

	@Column(name="nome_perfil")
	private String nomePerfil;

	//bi-directional many-to-many association to Permissao
	@ManyToMany
	@JoinTable(
		name="perfil_permissao"
		, joinColumns={
			@JoinColumn(name="cod_perfil")
			}
		, inverseJoinColumns={
			@JoinColumn(name="cod_permissao")
			}
		)
	private List<Permissao> permissaos;

	//bi-directional many-to-many association to UsuarioSpring
	@ManyToMany(mappedBy="perfils")
	private List<Usuario> usuarios;

	public Perfil() {
	}

	public Integer getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNomePerfil() {
		return this.nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public List<Permissao> getPermissaos() {
		return this.permissaos;
	}

	public void setPermissaos(List<Permissao> permissaos) {
		this.permissaos = permissaos;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}