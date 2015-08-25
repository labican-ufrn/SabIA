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

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "microrregiao")
public class Microrregiao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_microrregiao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_microrregiao")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "cod_ibge_microrregiao")
    private int codigoIBGE;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_mesorregiao")
    private Mesorregiao mesorregiao;
    
    //bidirecional
    @OneToMany(mappedBy = "microrregiao")
    private List<Cidade> cidades;
    
    //construtores
    public Microrregiao() {
    }

    public Microrregiao(String nome, int codigoIBGE, Mesorregiao mesorregiao) {
        this.nome = nome;
        this.codigoIBGE = codigoIBGE;
        this.mesorregiao.addMicrorregiao(getInstance());
    }

    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public int getCodigoIBGE() {
        return codigoIBGE;
    }
    
    public Mesorregiao getMesorregiao() {
        return mesorregiao;
    }
    
    public List<Cidade> getCidades() {
        return cidades;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public void setMesorregiao(Mesorregiao mesorregiao) {
        this.mesorregiao = mesorregiao;
    }
    
    //metodos para o relacionamento bidirecional
    public void addCidade(Cidade cidade) {
        cidade.setMicrorregiao(this);
        getCidades().add(cidade);
    }
    
    public void removeCidade(Cidade cidade) {
        cidade.setMicrorregiao(this);
        getCidades().remove(cidade);
    }
    
    private final Microrregiao getInstance() {
        return this;
    }
}
