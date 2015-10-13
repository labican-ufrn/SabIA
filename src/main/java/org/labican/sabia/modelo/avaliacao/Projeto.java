package org.labican.sabia.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "projeto")
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projeto")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_projeto")
    private String nome;
    
    //bidirecional
    @ManyToMany(mappedBy = "projetos")
    private List<Avaliador> avaliadores;
    
    //construtores
    public Projeto() {
    }

    public Projeto(String nome) {
        this.nome = nome;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Avaliador> getAvaliadores() {
        return avaliadores;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //metodos para o relacionamento bidirecional
    public void addAvaliador(Avaliador avaliador) {
        avaliador.getProjetos().add(this);
        this.getAvaliadores().add(avaliador);
    }
    
    public void removeAvaliador(Avaliador avaliador) {
        avaliador.getProjetos().remove(this);
        this.getAvaliadores().remove(avaliador);
    }
    
    private final Projeto getInstance() {
        return this;
    }
}
