package org.labican.sabia.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_cidade")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_cidade")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "cod_ibge_cidade")
    private int codigoIBGE;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_microrregiao")
    private Microrregiao microrregiao;
    
    //construtores
    public Cidade() {
    }

    public Cidade(String nome, int codigoIBGE, Microrregiao microrregiao) {
        this.nome = nome;
        this.codigoIBGE = codigoIBGE;
        this.microrregiao.addCidade(getInstance());
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public int getCodigoIBGEC() {
        return codigoIBGE;
    }
    
    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }
    
    //metodos para o relacionamento bidirecional
    private final Cidade getInstance() {
        return this;
    }
}
