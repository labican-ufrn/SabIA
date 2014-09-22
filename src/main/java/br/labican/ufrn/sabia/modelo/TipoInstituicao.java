package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_instituicao database table.
 * 
 */
@Entity
@Table(name="tipo_instituicao")
@NamedQuery(name="TipoInstituicao.findAll", query="SELECT t FROM TipoInstituicao t")
public class TipoInstituicao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_tipo_instituicao")
	private Integer idTipoInstituicao;

	@Column(name="nome_tipo_instituicao")
	private String nomeTipoInstituicao;

	//bi-directional many-to-one association to Instituicao
	@OneToMany(mappedBy="tipoInstituicao")
	private List<Instituicao> instituicaos;

	public TipoInstituicao() {
	}

	public Integer getIdTipoInstituicao() {
		return this.idTipoInstituicao;
	}

	public void setIdTipoInstituicao(Integer idTipoInstituicao) {
		this.idTipoInstituicao = idTipoInstituicao;
	}

	public String getNomeTipoInstituicao() {
		return this.nomeTipoInstituicao;
	}

	public void setNomeTipoInstituicao(String nomeTipoInstituicao) {
		this.nomeTipoInstituicao = nomeTipoInstituicao;
	}

	public List<Instituicao> getInstituicaos() {
		return this.instituicaos;
	}

	public void setInstituicaos(List<Instituicao> instituicaos) {
		this.instituicaos = instituicaos;
	}

	public Instituicao addInstituicao(Instituicao instituicao) {
		getInstituicaos().add(instituicao);
		instituicao.setTipoInstituicao(this);

		return instituicao;
	}

	public Instituicao removeInstituicao(Instituicao instituicao) {
		getInstituicaos().remove(instituicao);
		instituicao.setTipoInstituicao(null);

		return instituicao;
	}

}