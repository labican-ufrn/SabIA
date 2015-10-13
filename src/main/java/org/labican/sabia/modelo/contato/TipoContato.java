package org.labican.sabia.modelo.contato;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "tipo_contato")
public class TipoContato implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_contato")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_tipo_contato")
    private String nome;
    
    @Column(name = "expressao_regular")
    private String expressaoRegular;
    
    //construtores
    public TipoContato() {
    }

    public TipoContato(String nome, String expressaoRegular) {
        this.nome = nome;
        this.expressaoRegular = expressaoRegular;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getExpressaoRegular() {
        return expressaoRegular;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setExpressaoRegular(String expressaoRegular) {
        this.expressaoRegular = expressaoRegular;
    }
}
