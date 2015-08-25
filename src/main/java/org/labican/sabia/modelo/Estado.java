package org.labican.sabia.modelo;

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
import javax.validation.constraints.Size;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "estado")
public class Estado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_estado")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_estado")
    private String nome;
    
    @Basic(optional = false)
    @Size(min = 2, max = 2)
    @Column(name = "sigla_estado")
    private String sigla;
    
    @Basic(optional = false)
    @Column(name = "cod_ibge_estado")
    private int codigoIBGE;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_macrorregiao")
    private Macrorregiao macrorregiao;
    
    //bidirecional
    @OneToMany(mappedBy = "estado")
    private List<Mesorregiao> mesorregioes;
    
    //construtores
    public Estado() {
    }

    public Estado(String nome, String sigla, int codigoIBGE, Macrorregiao macrorregiao) {
        this.nome = nome;
        this.sigla = sigla;
        this.codigoIBGE = codigoIBGE;
        this.macrorregiao.addEstado(getInstance());
    }    

    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public int getCodigoIBGE() {
        return codigoIBGE;
    }
    
    public Macrorregiao getMacrorregiao() {
        return macrorregiao;
    }
    
    public List<Mesorregiao> getMesorregioes() {
        return mesorregioes;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public void setMacrorregiao(Macrorregiao macrorregiao) {
        this.macrorregiao = macrorregiao;
    }
    
    //metodos para o relacionamento bidirecional
    public void addMesorregiao(Mesorregiao mesorregiao) {
        mesorregiao.setEstado(this);
        getMesorregioes().add(mesorregiao);
    }
    
    public void removeMesorregiao(Mesorregiao mesorregiao) {
        mesorregiao.setEstado(null);
        getMesorregioes().remove(mesorregiao);
    }
    
    private final Estado getInstance() {
        return this;
    }
}
