package org.labican.sabia.modelo.unidadeensino;

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
@Table(name = "rede_ensino")
public class RedeEnsino implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rede_ensino")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_rede_ensino")
    private String nome;
    
    //construtores
    public RedeEnsino() {
    }
    
    public RedeEnsino(String nome) {
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
