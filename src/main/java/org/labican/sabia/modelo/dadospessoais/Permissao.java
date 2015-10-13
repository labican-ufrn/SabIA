package org.labican.sabia.modelo.dadospessoais;

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
@Table(name = "permissao")
public class Permissao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permissao")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_permissao")
    private String nome;
    
    //bidirecional
    @ManyToMany(mappedBy = "permissoes")
    private List<Usuario> usuarios;
    
    //construtores
    public Permissao() {
    }

    public Permissao(String nome) {
        this.nome = nome;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //metodos para o relacionamento bidirecional
    public void addUsuario(Usuario usuario) {
        usuario.getPermissoes().add(this);
        this.getUsuarios().add(usuario);
    }
    
    public void removeUsuario(Usuario usuario) {
        usuario.getPermissoes().remove(this);
        this.getUsuarios().remove(usuario);
    }
    
    private final Permissao getInstance() {
        return this;
    }
}
