package org.labican.sabia.modelo.avaliacao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "semana_avaliacao")
public class SemanaAvaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semana_avaliacao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "numero_semana_avaliacao")
    private int numero;
    
    @Basic(optional = false)
    @Column(name = "data_inicio_semana_avalicao")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    
    @Basic(optional = false)
    @Column(name = "data_fim_semana_avalicao")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    
    //bidirecional
    @ManyToMany(mappedBy = "semanasAvaliacoes")
    private List<Disponibilidade> disponibilidades;

    //construtores
    public SemanaAvaliacao() {
    }

    public SemanaAvaliacao(int numero, Date dataInicio, Date dataFim) {
        this.numero = numero;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public List<Disponibilidade> getDisponibilidades() {
        return disponibilidades;
    }
    
    //sets
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    //metodos para o relacionamento bidirecional  
    public void addDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidade.getSemanasAvaliacoes().add(this);
        this.getDisponibilidades().add(disponibilidade);
    }
    
    public void removeDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidade.getSemanasAvaliacoes().remove(this);
        this.getDisponibilidades().remove(disponibilidade);
    }
    
    private final SemanaAvaliacao getInstance() {
        return this;
    }
}
