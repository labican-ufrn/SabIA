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
@Table(name = "contato_pessoa")
public class ContatoPessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato_pessoa")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "valor_contato_pessoa")
    private String valor;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_tipo_contato")
    private TipoContato tipoContato;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_pessoa")
    private Pessoa pessoa;

    //construtores
    public ContatoPessoa() {
    } 

    public ContatoPessoa(String valor, TipoContato tipoContato, Pessoa pessoa) {
        this.valor = valor;
        this.tipoContato = tipoContato;
        this.pessoa = pessoa;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getValor() {
        return valor;
    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
    
    //sets
    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setTipoContato(TipoContato tipoContato) {
        this.tipoContato = tipoContato;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
