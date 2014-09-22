package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the confirmacao_avaliador database table.
 * 
 */
@Entity
@Table(name="confirmacao_avaliador")
@NamedQuery(name="ConfirmacaoAvaliador.findAll", query="SELECT c FROM ConfirmacaoAvaliador c")
public class ConfirmacaoAvaliador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_confirmacao_avaliador")
	private Integer idConfirmacaoAvaliador;

	@Temporal(TemporalType.DATE)
	@Column(name="data_confirmacao_avaliador")
	private Date dataConfirmacaoAvaliador;

	@Column(name="status_confirmacao_avaliador")
	private String statusConfirmacaoAvaliador;

	//bi-directional many-to-one association to Avaliador
	@ManyToOne
	@JoinColumn(name="cod_avaliador")
	private Avaliador avaliador;

	//bi-directional many-to-one association to Viagem
	@OneToMany(mappedBy="confirmacaoAvaliador")
	private List<Viagem> viagems;

	public ConfirmacaoAvaliador() {
	}

	public Integer getIdConfirmacaoAvaliador() {
		return this.idConfirmacaoAvaliador;
	}

	public void setIdConfirmacaoAvaliador(Integer idConfirmacaoAvaliador) {
		this.idConfirmacaoAvaliador = idConfirmacaoAvaliador;
	}

	public Date getDataConfirmacaoAvaliador() {
		return this.dataConfirmacaoAvaliador;
	}

	public void setDataConfirmacaoAvaliador(Date dataConfirmacaoAvaliador) {
		this.dataConfirmacaoAvaliador = dataConfirmacaoAvaliador;
	}

	public String getStatusConfirmacaoAvaliador() {
		return this.statusConfirmacaoAvaliador;
	}

	public void setStatusConfirmacaoAvaliador(String statusConfirmacaoAvaliador) {
		this.statusConfirmacaoAvaliador = statusConfirmacaoAvaliador;
	}

	public Avaliador getAvaliador() {
		return this.avaliador;
	}

	public void setAvaliador(Avaliador avaliador) {
		this.avaliador = avaliador;
	}

	public List<Viagem> getViagems() {
		return this.viagems;
	}

	public void setViagems(List<Viagem> viagems) {
		this.viagems = viagems;
	}

	public Viagem addViagem(Viagem viagem) {
		getViagems().add(viagem);
		viagem.setConfirmacaoAvaliador(this);

		return viagem;
	}

	public Viagem removeViagem(Viagem viagem) {
		getViagems().remove(viagem);
		viagem.setConfirmacaoAvaliador(null);

		return viagem;
	}

}