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
@Table(name = "mesorregiao")
public class Mesorregiao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesorregiao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_mesorregiao")
    private String nome;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_estado")
    private Estado estado;    
    
    //bidirecional
    @OneToMany(mappedBy = "mesorregiao")
    private List<Microrregiao> microrregioes;
    
    //construtores
    public Mesorregiao() {
    }

    public Mesorregiao(String nome, Estado estado) {
        this.nome = nome;
        this.estado.addMesorregiao(getInstance());
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public Estado getEstado() {
        return estado;
    }
    
    public List<Microrregiao> getMicrorregioes() {
        return microrregioes;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    //metodos para o relacionamento bidirecional
    public void addMicrorregiao(Microrregiao microrregiao) {
        microrregiao.setMesorregiao(this);
        this.getMicrorregioes().add(microrregiao);
    }
    
    public void removeMicrorregiao(Microrregiao microrregiao) {
        microrregiao.setMesorregiao(null);
        this.getMicrorregioes().remove(microrregiao);
    }
    
    private final Mesorregiao getInstance() {
        return this;
    }
}
