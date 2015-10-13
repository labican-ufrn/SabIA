package org.labican.sabia.modelo.viagem;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.labican.sabia.modelo.avaliador.Avaliador;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "confirmacao_avaliador")
public class ConfirmacaoAvaliador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_confirmacao_avaliador")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "status_confirmacao_avaliador")
    private String status;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "data_confirmacao_avaliador")    
    private Date dataConfirmacao;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_avaliador")
    private Avaliador avaliador;
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_viagem")
    private Viagem viagem;
    
    //construtores
    public ConfirmacaoAvaliador() {
    }

    public ConfirmacaoAvaliador(String status, Date dataConfirmacao, Avaliador avaliador) {
        this.status = status;
        this.dataConfirmacao = dataConfirmacao;
        this.avaliador = avaliador;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public Date getDataConfirmacao() {
        return dataConfirmacao;
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }

    public Viagem getViagem() {
        return viagem;
    }
    
    //sets
    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataConfirmacao(Date dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }
}
