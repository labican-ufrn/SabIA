package org.labican.sabia.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "logradouro")
    private String logradouro;
    
    @Basic(optional = false)
    @Column(name = "numero_endereco")
    private String numero;
    
    @Column(name = "complemento")
    private String complemento;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "ponto_referencia")
    private String pontoReferencia;
    
    @Column(name = "tipo_endereco")
    private String tipo;
    
    //unidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_cidade")
    private Cidade cidade;
    
    //contrutores
    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String complemento, String bairro, String pontoReferencia, String tipo, Cidade cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.pontoReferencia = pontoReferencia;
        this.tipo = tipo;
        this.cidade = cidade;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }
    
    public String getComplemento() {
        return complemento;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public String getPontoReferencia() {
        return pontoReferencia;
    }
    
    public String getTipo() {
        return tipo;
    }

    public Cidade getCidade() {
        return cidade;
    }
    
    //sets
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }  

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }    
}
