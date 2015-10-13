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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_microrregiao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_microrregiao")
    private String nome;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_mesorregiao")
    private Mesorregiao mesorregiao;
    
    //bidirecional
    @OneToMany(mappedBy = "microrregiao")
    private List<Municipio> municipios;
    
    //construtores
    public Microrregiao() {
    }

    public Microrregiao(String nome, Mesorregiao mesorregiao) {
        this.nome = nome;
        this.mesorregiao.addMicrorregiao(getInstance());
    }

    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public Mesorregiao getMesorregiao() {
        return mesorregiao;
    }
    
    public List<Municipio> getMunicipios() {
        return municipios;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMesorregiao(Mesorregiao mesorregiao) {
        this.mesorregiao = mesorregiao;
    }
    
    //metodos para o relacionamento bidirecional
    public void addMunicipio(Municipio municipio) {
        municipio.setMicrorregiao(this);
        this.getMunicipios().add(municipio);
    }
    
    public void removeMunicipio(Municipio municipio) {
        municipio.setMicrorregiao(this);
        this.getMunicipios().remove(municipio);
    }
    
    private final Microrregiao getInstance() {
        return this;
    }
}
