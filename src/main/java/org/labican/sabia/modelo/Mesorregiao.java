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
import javax.validation.constraints.Size;

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
    
    @Basic(optional = false)
    @Size(min = 3, max = 3)
    @Column(name = "sigla_mesorregiao")
    private String sigla;
    
    @Basic(optional = false)
    @Column(name = "cod_ibge_mesorregiao")
    private int codigoIBGE;
    
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

    public Mesorregiao(String nome, String sigla, int codigoIBGE, Estado estado) {
        this.nome = nome;
        this.sigla = sigla;
        this.codigoIBGE = codigoIBGE;
        this.estado.addMesorregiao(getInstance());
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public int getCodigoIBGE() {
        return codigoIBGE;
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

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
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
