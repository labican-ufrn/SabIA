package org.labican.sabia.modelo.localizacao;

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
@Table(name = "distrito")
public class Distrito implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distrito")
    private Integer id;
    
    @Basic(optional = false)    
    @Column(name = "nome_distrito")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "geocod_distrito")
    private int geoCodigo;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_municipio")
    private Municipio municipio;
    
    //bidirecional
    @OneToMany(mappedBy = "distrito")
    private List<Localidade> localidades;

    //construtores
    public Distrito() {
    }

    public Distrito(String nome, int geoCodigo, Municipio municipio) {
        this.nome = nome;
        this.geoCodigo = geoCodigo;
        this.municipio = municipio;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getGeoCodigo() {
        return geoCodigo;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGeoCodigo(int geoCodigo) {
        this.geoCodigo = geoCodigo;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    
    //metodos para o relacionamento bidirecional
    public void addLocalidade(Localidade localidade) {
        localidade.setDistrito(this);
        this.getLocalidades().add(localidade);
    }
    
    public void removeLocalidade(Localidade localidade) {
        localidade.setDistrito(null);
        this.getLocalidades().remove(localidade);
    }
    
    private final Distrito getInstance() {
        return this;
    }
}
