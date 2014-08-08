package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Rummenigge Maia
 */
@Entity
public class Autorizacao implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    private String perfil;
    @ManyToMany(mappedBy = "autorizacoes")
    private List<Usuario> usuarios;

    public Autorizacao() {
    }

    public Autorizacao(Long id, String role) {
        this.id = id;
        this.perfil = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
