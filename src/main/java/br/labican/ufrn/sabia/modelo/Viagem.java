package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the viagem database table.
 * 
 */
@Entity
@NamedQuery(name="Viagem.findAll", query="SELECT v FROM Viagem v")
public class Viagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_viagem")
	private Integer idViagem;

	@Temporal(TemporalType.DATE)
	@Column(name="data_chegada_viagem")
	private Date dataChegadaViagem;

	@Temporal(TemporalType.DATE)
	@Column(name="data_saida_viagem")
	private Date dataSaidaViagem;

	private String percurso;

	//bi-directional many-to-one association to Atividade
	@OneToMany(mappedBy="viagem")
	private List<Atividade> atividades;

	//bi-directional many-to-one association to ConfirmacaoAvaliador
	@ManyToOne
	@JoinColumn(name="cod_confirmacao_avaliador")
	private ConfirmacaoAvaliador confirmacaoAvaliador;

	public Viagem() {
	}

	public Integer getIdViagem() {
		return this.idViagem;
	}

	public void setIdViagem(Integer idViagem) {
		this.idViagem = idViagem;
	}

	public Date getDataChegadaViagem() {
		return this.dataChegadaViagem;
	}

	public void setDataChegadaViagem(Date dataChegadaViagem) {
		this.dataChegadaViagem = dataChegadaViagem;
	}

	public Date getDataSaidaViagem() {
		return this.dataSaidaViagem;
	}

	public void setDataSaidaViagem(Date dataSaidaViagem) {
		this.dataSaidaViagem = dataSaidaViagem;
	}

	public String getPercurso() {
		return this.percurso;
	}

	public void setPercurso(String percurso) {
		this.percurso = percurso;
	}

	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public Atividade addAtividade(Atividade atividade) {
		getAtividades().add(atividade);
		atividade.setViagem(this);

		return atividade;
	}

	public Atividade removeAtividade(Atividade atividade) {
		getAtividades().remove(atividade);
		atividade.setViagem(null);

		return atividade;
	}

	public ConfirmacaoAvaliador getConfirmacaoAvaliador() {
		return this.confirmacaoAvaliador;
	}

	public void setConfirmacaoAvaliador(ConfirmacaoAvaliador confirmacaoAvaliador) {
		this.confirmacaoAvaliador = confirmacaoAvaliador;
	}

}