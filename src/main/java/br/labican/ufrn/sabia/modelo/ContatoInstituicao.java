package br.labican.ufrn.sabia.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the contato_instituicao database table.
 *
 */
@Entity
@Table(name = "contato_instituicao")
@NamedQuery(name = "ContatoInstituicao.findAll", query = "SELECT c FROM ContatoInstituicao c")
public class ContatoInstituicao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_contato_instituicao")
    private Integer idContatoInstituicao;

    @Column(name = "cod_tipo_contato")
    private Integer codTipoContato;

    @Column(name = "valor_contato_instituicao")
    private String valorContatoInstituicao;

    // bi-directional many-to-one association to Instituicao
    @ManyToOne
    @JoinColumn(name = "cod_instituicao")
    private Instituicao instituicao;

    public ContatoInstituicao() {
    }

    public Integer getIdContatoInstituicao() {
        return this.idContatoInstituicao;
    }

    public void setIdContatoInstituicao(Integer idContatoInstituicao) {
        this.idContatoInstituicao = idContatoInstituicao;
    }

    public Integer getCodTipoContato() {
        return this.codTipoContato;
    }

    public void setCodTipoContato(Integer codTipoContato) {
        this.codTipoContato = codTipoContato;
    }

    public String getValorContatoInstituicao() {
        return this.valorContatoInstituicao;
    }

    public void setValorContatoInstituicao(String valorContatoInstituicao) {
        this.valorContatoInstituicao = valorContatoInstituicao;
    }

    public Instituicao getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

}