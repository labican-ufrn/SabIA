package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the equipe database table.
 * 
 */
@Entity
@NamedQuery(name="Equipe.findAll", query="SELECT e FROM Equipe e")
public class Equipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_equipe")
	private Integer idEquipe;

	//bi-directional many-to-one association to Avaliacao
	@OneToMany(mappedBy="equipe")
	private List<Avaliacao> avaliacaos;

	//bi-directional many-to-one association to Avaliacao
	@ManyToOne
	@JoinColumn(name="cod_avaliacao")
	private Avaliacao avaliacao;

	//bi-directional many-to-many association to Avaliador
	@ManyToMany
	@JoinTable(
		name="integrante"
		, joinColumns={
			@JoinColumn(name="cod_equipe")
			}
		, inverseJoinColumns={
			@JoinColumn(name="cod_avaliador")
			}
		)
	private List<Avaliador> avaliadors;

	public Equipe() {
	}

	public Integer getIdEquipe() {
		return this.idEquipe;
	}

	public void setIdEquipe(Integer idEquipe) {
		this.idEquipe = idEquipe;
	}

	public List<Avaliacao> getAvaliacaos() {
		return this.avaliacaos;
	}

	public void setAvaliacaos(List<Avaliacao> avaliacaos) {
		this.avaliacaos = avaliacaos;
	}

	public Avaliacao addAvaliacao(Avaliacao avaliacao) {
		getAvaliacaos().add(avaliacao);
		avaliacao.setEquipe(this);

		return avaliacao;
	}

	public Avaliacao removeAvaliacao(Avaliacao avaliacao) {
		getAvaliacaos().remove(avaliacao);
		avaliacao.setEquipe(null);

		return avaliacao;
	}

	public Avaliacao getAvaliacao() {
		return this.avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Avaliador> getAvaliadors() {
		return this.avaliadors;
	}

	public void setAvaliadors(List<Avaliador> avaliadors) {
		this.avaliadors = avaliadors;
	}

}