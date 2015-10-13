package org.labican.sabia.modelo.avaliador;

import org.labican.sabia.modelo.unidadeensino.UnidadeEnsino;
import org.labican.sabia.modelo.dadospessoais.Pessoa;
import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.labican.sabia.modelo.avaliacao.Disponibilidade;
import org.labican.sabia.modelo.avaliacao.Equipe;
import org.labican.sabia.modelo.avaliacao.Projeto;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "avaliador")
public class Avaliador implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliador")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "data_engresso")
    private int dataEngresso;
    
    @Basic(optional = false)
    @Column(name = "siape_avaliador")
    private String siape;
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_pessoa")
    private Pessoa pessoa;
    
    //unidirecional
    @ManyToOne(optional = false)    
    @JoinColumn(name = "cod_titulacao")
    private Titulacao titulacao;
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_nivel")
    private Nivel nivel;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_ensino")
    private UnidadeEnsino unidadeEnsino;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cargo")
    private Cargo cargo; 
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_status_avaliador")
    private StatusAvaliador statusAvaliador;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "avaliador_eixo") //nome da entidade fraca no banco
    private List<EixoTecnologico> eixosTecnologicos;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "avaliador_projeto") //nome da entidade fraca no banco
    private List<Projeto> projetos;
    
    //bidirecional
    @ManyToMany(mappedBy = "avaliadores")
    private List<Equipe> equipes;
    
    //bidirecional
    @ManyToMany(mappedBy = "avaliadores")
    private List<Disponibilidade> disponibilidades;
    
    //construtores
    public Avaliador() {
    }

    public Avaliador(int dataEngresso, String siape, Pessoa pessoa, Titulacao titulacao, Nivel nivel, UnidadeEnsino unidadeEnsino, Cargo cargo, StatusAvaliador statusAvaliador) {
        this.dataEngresso = dataEngresso;
        this.siape = siape;
        this.pessoa = pessoa;
        this.titulacao = titulacao;
        this.nivel = nivel;
        this.unidadeEnsino = unidadeEnsino;
        this.cargo = cargo;
        this.statusAvaliador = statusAvaliador;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public int getDataEngresso() {
        return dataEngresso;
    }

    public String getSiape() {
        return siape;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Titulacao getTitulacao() {
        return titulacao;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public UnidadeEnsino getUnidadeEnsino() {
        return unidadeEnsino;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public StatusAvaliador getStatusAvaliador() {
        return statusAvaliador;
    }

    public List<EixoTecnologico> getEixosTecnologicos() {
        return eixosTecnologicos;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public List<Disponibilidade> getDisponibilidades() {
        return disponibilidades;
    }
    
    //sets
    public void setDataEngresso(int dataEngresso) {
        this.dataEngresso = dataEngresso;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setTitulacao(Titulacao titulacao) {
        this.titulacao = titulacao;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void setUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        this.unidadeEnsino = unidadeEnsino;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setStatusAvaliador(StatusAvaliador statusAvaliador) {
        this.statusAvaliador = statusAvaliador;
    }
    
    //metodos para o relacionamento bidirecional
    //equipes
    public void addEquipe(Equipe equipe) {
        equipe.getAvaliadores().add(this);
        this.getEquipes().add(equipe);
    }
    
    public void removeEquipe(Equipe equipe) {
        equipe.getAvaliadores().remove(this);
        this.getEquipes().remove(equipe);
    }
    
    //disponibilidades
    public void addDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidade.getAvaliadores().add(this);
        this.getDisponibilidades().add(disponibilidade);
    }
    
    public void removeDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidade.getAvaliadores().remove(this);
        this.getDisponibilidades().remove(disponibilidade);
    }
    
    private final Avaliador getInstance() {
        return this;
    }
}
