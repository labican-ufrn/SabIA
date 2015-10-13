package org.labican.sabia.modelo.contato;

import org.labican.sabia.modelo.unidadeensino.UnidadeEnsino;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "contato_unidade_ensino")
public class ContatoUnidadeEnsino implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato_unidade_ensino")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "valor_contato_unidade_ensino")
    private String valor;
    
    @Basic(optional = false)
    @Column(name = "descricai")
    private String descricao;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_tipo_contato")
    private TipoContato tipoContato;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_unidade_ensino")
    private UnidadeEnsino unidadeEnsino;
    
    //construtores
    public ContatoUnidadeEnsino() {
    }

    public ContatoUnidadeEnsino(String valor, String descricao, TipoContato tipoContato, UnidadeEnsino unidadeEnsino) {
        this.valor = valor;
        this.descricao = descricao;
        this.tipoContato = tipoContato;
        this.unidadeEnsino = unidadeEnsino;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public UnidadeEnsino getUnidadeEnsino() {
        return unidadeEnsino;
    }
    
    //sets
    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipoContato(TipoContato tipoContato) {
        this.tipoContato = tipoContato;
    }

    public void setUnidadeEnsino(UnidadeEnsino unidadeEnsino) {
        this.unidadeEnsino = unidadeEnsino;
    }
}
