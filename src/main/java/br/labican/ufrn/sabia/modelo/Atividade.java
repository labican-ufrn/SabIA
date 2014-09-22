package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the atividade database table.
 * 
 */
@Entity
@NamedQuery(name="Atividade.findAll", query="SELECT a FROM Atividade a")
public class Atividade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_atividade")
	private Integer idAtividade;

	@Temporal(TemporalType.DATE)
	@Column(name="data_atividade")
	private Date dataAtividade;

	@Column(name="descricao_atividade")
	private String descricaoAtividade;

	//bi-directional many-to-one association to Viagem
	@ManyToOne
	@JoinColumn(name="cod_viagem")
	private Viagem viagem;

	public Atividade() {
	}

	public Integer getIdAtividade() {
		return this.idAtividade;
	}

	public void setIdAtividade(Integer idAtividade) {
		this.idAtividade = idAtividade;
	}

	public Date getDataAtividade() {
		return this.dataAtividade;
	}

	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}

	public String getDescricaoAtividade() {
		return this.descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}

	public Viagem getViagem() {
		return this.viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

}