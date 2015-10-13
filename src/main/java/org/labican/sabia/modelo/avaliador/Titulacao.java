package org.labican.sabia.modelo;

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
@Table(name = "titulacao")
public class Titulacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_titulacao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_titulacao")
    private String nome;
    
    //construtores
    public Titulacao() {
    }

    public Titulacao(String nome) {
        this.nome = nome;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }
}
