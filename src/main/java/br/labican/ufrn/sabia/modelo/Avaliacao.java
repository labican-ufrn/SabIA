package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the avaliacao database table.
 * 
 */
@Entity
@NamedQuery(name="Avaliacao.findAll", query="SELECT a FROM Avaliacao a")
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_avaliacao")
	private Integer idAvaliacao;

	//bi-directional many-to-one association to Equipe
	@ManyToOne
	@JoinColumn(name="cod_equipe")
	private Equipe equipe;

	//bi-directional many-to-one association to Instituicao
	@ManyToOne
	@JoinColumn(name="cod_instituicao")
	private Instituicao instituicao;

	//bi-directional many-to-one association to Projeto
	@ManyToOne
	@JoinColumn(name="cod_projeto")
	private Projeto projeto;

	//bi-directional many-to-one association to SemanaAvaliacao
	@ManyToOne
	@JoinColumn(name="cod_semana_avaliacao")
	private SemanaAvaliacao semanaAvaliacao;

	//bi-directional many-to-one association to Equipe
	@OneToMany(mappedBy="avaliacao")
	private List<Equipe> equipes;

	public Avaliacao() {
	}

	public Integer getIdAvaliacao() {
		return this.idAvaliacao;
	}

	public void setIdAvaliacao(Integer idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public Equipe getEquipe() {
		return this.equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Instituicao getInstituicao() {
		return this.instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Projeto getProjeto() {
		return this.projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public SemanaAvaliacao getSemanaAvaliacao() {
		return this.semanaAvaliacao;
	}

	public void setSemanaAvaliacao(SemanaAvaliacao semanaAvaliacao) {
		this.semanaAvaliacao = semanaAvaliacao;
	}

	public List<Equipe> getEquipes() {
		return this.equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public Equipe addEquipe(Equipe equipe) {
		getEquipes().add(equipe);
		equipe.setAvaliacao(this);

		return equipe;
	}

	public Equipe removeEquipe(Equipe equipe) {
		getEquipes().remove(equipe);
		equipe.setAvaliacao(null);

		return equipe;
	}

}