package org.labican.sabia.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    
    @Basic(optional = false)
    @Column(name = "senha")
    private String senha;
    
    @Basic(optional = false)
    @Column(name = "ativado")
    private boolean ativado;
    
    //bidirecional
    @OneToOne(mappedBy = "usuario")
    private Pessoa pessoa;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "perfil") //nome da entidade fraca no banco
    private List<Permissao> permissoes;

    //construtores
    public Usuario() {
    }

    public Usuario(String login, String senha, boolean ativado) {
        this.login = login;
        this.senha = senha;
        this.ativado = ativado;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public boolean getAtivado() {
        return ativado;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }
    
    //sets
    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }    
    
    //metodos para o relacionamento bidirecional
    public void addPessoa(Pessoa pessoa) {
        pessoa.setUsuario(this);
        this.setPessoa(pessoa);
    }
    
    public void removePessoa(Pessoa pessoa) {
        pessoa.setUsuario(null);
        this.setPessoa(null);
    }
    
    private final Usuario getInstance() {
        return this;
    }
}
