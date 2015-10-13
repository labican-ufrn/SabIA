package org.labican.sabia.modelo.avaliacao;

import org.labican.sabia.modelo.unidadeensino.UnidadeEnsino;
import java.io.Serializable;
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

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_avaliacao")
    private Integer id;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_projeto")
    private Projeto projeto;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_semana_avaliacao")
    private SemanaAvaliacao semanaAvaliacao;
    
    //bidirecional
    @OneToOne(mappedBy = "avaliacao")
    private Equipe equipe;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_unidade_ensino ")
    private UnidadeEnsino unidadeEnsino; 
    
    //construtores
    public Avaliacao() {
    }

    public Avaliacao(Projeto projeto, SemanaAvaliacao semanaAvaliacao, Equipe equipe, UnidadeEnsino unidadeEnsino) {
        this.projeto = projeto;
        this.semanaAvaliacao = semanaAvaliacao;
        this.equipe = equipe;
        this.unidadeEnsino = unidadeEnsino;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public SemanaAvaliacao getSemanaAvaliacao() {
        return semanaAvaliacao;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public UnidadeEnsino getUnidadeEnsino() {
        return unidadeEnsino;
    }
    
    //sets
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public void setSemanaAvaliacao(SemanaAvaliacao semanaAvaliacao) {
        this.semanaAvaliacao = semanaAvaliacao;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public void setUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        this.unidadeEnsino = unidadeEnsino;
    }
    
    //metodos para o relacionamento bidirecional
    public void addEquipe(Equipe equipe) {
        equipe.setAvaliacao(this);
        this.setEquipe(equipe);
    }
    
    public void removeEquipe(Equipe equipe) {
        equipe.setAvaliacao(null);
        this.setEquipe(null);
    }
    
    private final Avaliacao getInstance() {
        return this;
    }
}
