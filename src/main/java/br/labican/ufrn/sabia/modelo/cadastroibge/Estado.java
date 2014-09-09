package br.labican.ufrn.sabia.modelo.cadastroibge;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estado database table.
 * 
 */
@Entity
@NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_estado")
	private Integer idEstado;

	@Column(name="cod_ibge_estado")
	private Integer codIbgeEstado;

	@Column(name="nome_estado")
	private String nomeEstado;

	@Column(name="sigla_estado")
	private String siglaEstado;

	//bi-directional many-to-one association to Macrorregiao
	@ManyToOne
	@JoinColumn(name="cod_macrorregiao")
	private Macrorregiao macrorregiao;

	//bi-directional many-to-one association to Mesorregiao
	@OneToMany(mappedBy="estado")
	private List<Mesorregiao> mesorregiaos;

	public Estado() {
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getCodIbgeEstado() {
		return this.codIbgeEstado;
	}

	public void setCodIbgeEstado(Integer codIbgeEstado) {
		this.codIbgeEstado = codIbgeEstado;
	}

	public String getNomeEstado() {
		return this.nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public String getSiglaEstado() {
		return this.siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public Macrorregiao getMacrorregiao() {
		return this.macrorregiao;
	}

	public void setMacrorregiao(Macrorregiao macrorregiao) {
		this.macrorregiao = macrorregiao;
	}

	public List<Mesorregiao> getMesorregiaos() {
		return this.mesorregiaos;
	}

	public void setMesorregiaos(List<Mesorregiao> mesorregiaos) {
		this.mesorregiaos = mesorregiaos;
	}

	public Mesorregiao addMesorregiao(Mesorregiao mesorregiao) {
		getMesorregiaos().add(mesorregiao);
		mesorregiao.setEstado(this);

		return mesorregiao;
	}

	public Mesorregiao removeMesorregiao(Mesorregiao mesorregiao) {
		getMesorregiaos().remove(mesorregiao);
		mesorregiao.setEstado(null);

		return mesorregiao;
	}

}