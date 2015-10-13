package org.labican.sabia.modelo.localizacao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "municipio")
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_municipio")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "geocod_municipio")
    private int geoCodigo;
    
    @Basic(optional = false)
    @Column(name = "populacao")
    private int populacao; //população estimada de acordo com as pesquisas do IBGE.
    
    @Basic(optional = false)
    @Column(name = "ano_populacao") 
    private int anoPopulacao; //ano em que a pesquisa do IBGE foi feita.
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_microrregiao")
    private Microrregiao microrregiao;
    
    //bidirecional
    @OneToMany(mappedBy = "municipio")
    private List<Distrito> distritos;
    
    //construtores
    public Municipio() {
    }

    public Municipio(String nome, int geoCodigo, int populacao, int anoPopulacao, Microrregiao microrregiao) {
        this.nome = nome;
        this.geoCodigo = geoCodigo;
        this.populacao = populacao;
        this.anoPopulacao = anoPopulacao;
        this.microrregiao = microrregiao;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public int getGeoCodigo() {
        return geoCodigo;
    }

    public int getPopulacao() {
        return populacao;
    }

    public int getAnoPopulacao() {
        return anoPopulacao;
    }
    
    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }

    public List<Distrito> getDistritos() {
        return distritos;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGeoCodigo(int geoCodigo) {
        this.geoCodigo = geoCodigo;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public void setAnoPopulacao(int anoPopulacao) {
        this.anoPopulacao = anoPopulacao;
    }    

    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }
    
    //metodos para o relacionamento bidirecional
    public void addDistrito(Distrito distrito) {
        distrito.setMunicipio(this);
        this.getDistritos().add(distrito);
    }
    
    public void removeDistrito(Distrito distrito) {
        distrito.setMunicipio(null);
        this.getDistritos().remove(distrito);
    }
    
    private final Municipio getInstance() {
        return this;
    }
}