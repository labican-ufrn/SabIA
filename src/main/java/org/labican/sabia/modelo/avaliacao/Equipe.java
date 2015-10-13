package org.labican.sabia.modelo.avaliacao;

import org.labican.sabia.modelo.avaliador.Avaliador;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hyago
 */
@Entity
@Table(name = "equipe")
public class Equipe implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "numero_equipe")
    private int numero;
    
    //bidirecional: dono do relacionamento
    @OneToOne(optional = false)
    @JoinColumn(name = "cod_avaliacao")
    private Avaliacao avaliacao;
    
    //bidirecional: lado dominante no relacionamento muitos para muitos
    @ManyToMany
    @JoinTable(name = "integrante") //nome da entidade fraca no banco
    private List<Avaliador> avaliadores;
    
    //construtores
    public Equipe() {
    }

    public Equipe(int numero, Avaliacao avaliacao) {
        this.numero = numero;
        this.avaliacao = avaliacao;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public List<Avaliador> getAvaliadores() {
        return avaliadores;
    }
    
    //sets
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }
}
