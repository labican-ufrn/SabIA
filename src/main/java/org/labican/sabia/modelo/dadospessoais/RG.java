package org.labican.sabia.modelo.dadospessoais;

import org.labican.sabia.modelo.localizacao.Estado;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "rg")
public class RG implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rg")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "numero_rg")
    private String numero;
    
    @Basic(optional = false)
    @Column(name = "orgao_emissor")
    private String orgaoEmissor;
    
    @Basic(optional = false)
    @Column(name = "data_emissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    
    //unidirecional
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_estado")
    private Estado estado;
    
    //construtores
    public RG() {
    }

    public RG(String numero, String orgaoEmissor, Date dataEmissao, Estado estado) {
        this.numero = numero;
        this.orgaoEmissor = orgaoEmissor;
        this.dataEmissao = dataEmissao;
        this.estado = estado;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public Estado getEstado() {
        return estado;
    }
    
    //sets
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
