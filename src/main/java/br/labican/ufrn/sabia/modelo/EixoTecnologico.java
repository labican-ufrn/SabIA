package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eixo_tecnologico database table.
 * 
 */
@Entity
@Table(name="eixo_tecnologico")
@NamedQuery(name="EixoTecnologico.findAll", query="SELECT e FROM EixoTecnologico e")
public class EixoTecnologico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_eixo_tecnologico")
	private Integer idEixoTecnologico;

	@Column(name="nome_eixo_tecnologico")
	private String nomeEixoTecnologico;

	//bi-directional many-to-many association to Avaliador
	@ManyToMany
	@JoinTable(
		name="avaliador_eixo"
		, joinColumns={
			@JoinColumn(name="cod_eixo_tecnologico")
			}
		, inverseJoinColumns={
			@JoinColumn(name="cod_avaliador")
			}
		)
	private List<Avaliador> avaliadors;

	public EixoTecnologico() {
	}

	public Integer getIdEixoTecnologico() {
		return this.idEixoTecnologico;
	}

	public void setIdEixoTecnologico(Integer idEixoTecnologico) {
		this.idEixoTecnologico = idEixoTecnologico;
	}

	public String getNomeEixoTecnologico() {
		return this.nomeEixoTecnologico;
	}

	public void setNomeEixoTecnologico(String nomeEixoTecnologico) {
		this.nomeEixoTecnologico = nomeEixoTecnologico;
	}

	public List<Avaliador> getAvaliadors() {
		return this.avaliadors;
	}

	public void setAvaliadors(List<Avaliador> avaliadors) {
		this.avaliadors = avaliadors;
	}

}