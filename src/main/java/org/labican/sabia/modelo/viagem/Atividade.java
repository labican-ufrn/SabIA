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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "atividade")
public class Atividade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atividade")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "data_atividade")
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @Basic(optional = false)
    @Column(name = "descricao_atividade")
    private String descricao;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_viagem")
    private Viagem viagem;
    
    //construtores
    public Atividade() {
    }

    public Atividade(Date data, String descricao, Viagem viagem) {
        this.data = data;
        this.descricao = descricao;
        this.viagem = viagem;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Viagem getViagem() {
        return viagem;
    }
    
    //sets
    public void setData(Date data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }
}
