package org.labican.sabia.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "macrorregiao")
public class Macrorregiao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_macrorregiao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_macrorregiao")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "cod_mec_macrorregiao")
    private int codigoMEC;
    
    //bidirecional
    @OneToMany(mappedBy = "macrorregiao")
    private List<Estado> estados;
    
    //construtores
    public Macrorregiao() {
    }

    public Macrorregiao(String nome, int codigoMEC) {
        this.nome = nome;
        this.codigoMEC = codigoMEC;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public int getCodigoIBGE() {
        return codigoMEC;
    }
    
    public List<Estado> getEstados() {
        return estados;
    }
    
    //sets    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCodigoIBGE(int codigoMEC) {
        this.codigoMEC = codigoMEC;
    }
    
    //metodos para o relacionamento bidirecional
    public void addEstado(Estado estado) {
        estado.setMacrorregiao(this);
        this.getEstados().add(estado);
    }
    
    public void removeEstado(Estado estado) {
        estado.setMacrorregiao(null);
        this.getEstados().remove(estado);
    }
    
    private final Macrorregiao getInstance() {
        return this;
    }
}
