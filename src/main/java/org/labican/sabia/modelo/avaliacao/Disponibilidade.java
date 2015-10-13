/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.labican.sabia.modelo.avaliacao;

import org.labican.sabia.modelo.avaliador.Avaliador;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "disponibilidade")
public class Disponibilidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidade")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "avaliador_disponibilidade") //nome da entidade fraca no banco
    private List<Avaliador> avaliadores;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "disponibilidade_semana") //nome da entidade fraca no banco
    private List<SemanaAvaliacao> semanasAvaliacoes;

    //construtores
    public Disponibilidade() {
    }

    public Disponibilidade(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public List<Avaliador> getAvaliadores() {
        return avaliadores;
    }

    public List<SemanaAvaliacao> getSemanasAvaliacoes() {
        return semanasAvaliacoes;
    }
    
    //sets
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
