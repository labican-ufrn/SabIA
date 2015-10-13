package org.labican.sabia.modelo.viagem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "viagem")
public class Viagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viagem")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "percurso")
    private String percurso;
    
    @Basic(optional = false)
    @Column(name = "data_saida_viagem")
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    
    @Basic(optional = false)
    @Column(name = "data_chegada_viagem")
    @Temporal(TemporalType.DATE)
    private Date dataChegada;
    
    //bidirecional
    @OneToOne(mappedBy = "viagem")
    private ConfirmacaoAvaliador confirmacao;
    
    //bidirecional
    @OneToMany(mappedBy = "viagem")
    private List<Atividade> atividades;

    //contrutores
    public Viagem() {
    }

    public Viagem(String percurso, Date dataSaida, Date dataChegada) {
        this.percurso = percurso;
        this.dataSaida = dataSaida;
        this.dataChegada = dataChegada;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getPercurso() {
        return percurso;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public ConfirmacaoAvaliador getConfirmacao() {
        return confirmacao;
    }
    
    public List<Atividade> getAtividades() {
        return atividades;
    }
    
    //sets
    public void setPercurso(String percurso) {
        this.percurso = percurso;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public void setConfirmacao(ConfirmacaoAvaliador confirmacao) {
        this.confirmacao = confirmacao;
    }
    
    //metodos para o relacionamento bidirecional
    //confirmação
    public void addConfirmacao(ConfirmacaoAvaliador confirmacao) {
        confirmacao.setViagem(this);
        this.setConfirmacao(confirmacao);
    }
    
    public void removeConfirmacao(ConfirmacaoAvaliador confirmacao) {
        confirmacao.setViagem(null);
        this.setConfirmacao(null);
    }
    
    //atividades
    public void addAtividade(Atividade atividade) {
        atividade.setViagem(this);
        this.getAtividades().add(atividade);
    }
    
    public void removeAtividade(Atividade atividade) {
        atividade.setViagem(null);
        this.getAtividades().remove(atividade);
    }
    
    private final Viagem getInstance() {
        return this;
    }
}
