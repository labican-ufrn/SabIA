package org.labican.sabia.modelo;

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

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "status_avaliador")
public class StatusAvaliador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_avaliador")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "justificativa_status_avaliador")
    private String justificativa;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "data_status_avaliador")
    private Date dataStatus;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_tipo_status_avaliador")
    private TipoStatusAvaliador tipoStatus;
    
    //bidirecional
    @OneToOne(mappedBy = "status")
    private Avaliador avaliador;
    
    //construtores
    public StatusAvaliador() {
    }

    public StatusAvaliador(String justificativa, Date dataStatus, TipoStatusAvaliador tipoStatus) {
        this.justificativa = justificativa;
        this.dataStatus = dataStatus;
        this.tipoStatus = tipoStatus;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public Date getDataStatus() {
        return dataStatus;
    }

    public TipoStatusAvaliador getTipoStatus() {
        return tipoStatus;
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }
    
    //sets
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public void setDataStatus(Date dataStatus) {
        this.dataStatus = dataStatus;
    }

    public void setTipoStatus(TipoStatusAvaliador tipoStatus) {
        this.tipoStatus = tipoStatus;
    }

    public void setAvaliador(Avaliador avaliador) {
        this.avaliador = avaliador;
    }
    
    //metodos para o relacionamento bidirecional
    public void addAvaliador(Avaliador avaliador) {
        avaliador.setStatusAvaliador(this);
        this.setAvaliador(avaliador);
    }
    
    public void removeAvaliador(Avaliador avaliador) {
        avaliador.setStatusAvaliador(null);
        this.setAvaliador(null);
    }
    
    private final StatusAvaliador getInstance() {
        return this;
    }
}
