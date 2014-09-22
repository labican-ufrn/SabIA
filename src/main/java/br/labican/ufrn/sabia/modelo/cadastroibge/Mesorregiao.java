package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the mesorregiao database table.
 * 
 */
@Entity
@NamedQuery(name = "Mesorregiao.findAll", query = "SELECT m FROM Mesorregiao m")
public class Mesorregiao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_mesorregiao")
	private Integer idMesorregiao;

	@Column(name = "cod_ibge_mesorregiao")
	private Integer codIbgeMesorregiao;

	@Column(name = "nome_mesorregiao")
	private String nomeMesorregiao;

	@Column(name = "sigla_mesorregiao")
	private String siglaMesorregiao;

	// bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name = "cod_estado")
	private Estado estado;

	// bi-directional many-to-one association to Microrregiao
	@OneToMany(mappedBy = "mesorregiao", cascade = CascadeType.ALL)
	private List<Microrregiao> microrregiaos;

	public Mesorregiao() {
	}

	public Integer getIdMesorregiao() {
		return this.idMesorregiao;
	}

	public void setIdMesorregiao(Integer idMesorregiao) {
		this.idMesorregiao = idMesorregiao;
	}

	public Integer getCodIbgeMesorregiao() {
		return this.codIbgeMesorregiao;
	}

	public void setCodIbgeMesorregiao(Integer codIbgeMesorregiao) {
		this.codIbgeMesorregiao = codIbgeMesorregiao;
	}

	public String getNomeMesorregiao() {
		return this.nomeMesorregiao;
	}

	public void setNomeMesorregiao(String nomeMesorregiao) {
		this.nomeMesorregiao = nomeMesorregiao;
	}

	public String getSiglaMesorregiao() {
		return this.siglaMesorregiao;
	}

	public void setSiglaMesorregiao(String siglaMesorregiao) {
		this.siglaMesorregiao = siglaMesorregiao;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Microrregiao> getMicrorregiaos() {
		return this.microrregiaos;
	}

	public void setMicrorregiaos(List<Microrregiao> microrregiaos) {
		this.microrregiaos = microrregiaos;
	}

	public Microrregiao addMicrorregiao(Microrregiao microrregiao) {
		getMicrorregiaos().add(microrregiao);
		microrregiao.setMesorregiao(this);

		return microrregiao;
	}

	public Microrregiao removeMicrorregiao(Microrregiao microrregiao) {
		getMicrorregiaos().remove(microrregiao);
		microrregiao.setMesorregiao(null);

		return microrregiao;
	}
}