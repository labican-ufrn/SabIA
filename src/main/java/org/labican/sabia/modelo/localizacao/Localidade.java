package org.labican.sabia.modelo.localizacao;

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
@Table(name = "localidade")
public class Localidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidade")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "nome_localidade")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "tipo_localidade")
    private String tipo; //rural ou urbano
    
    @Basic(optional = false)
    @Column(name = "geocodigo")
    private int geoCodigo; //Este geocódigo é completo (UF, Município, Distrito, Subdistrito, Setor).
    
    @Column(name = "latitude")
    private double latitude; //16 números, sendo 13 casas decimais.
    
    @Column(name = "longitude")
    private double longitude; //16 números, sendo 13 casas decimais.
    
    @Column(name = "altitude")
    private double altitude; //9 números, sendo 6 casas decimais.
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_distrito")
    private Distrito distrito;
    
    //bidirecional: dono do relacionamento
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_categoria")
    private Categoria categoria;

    //construtores
    public Localidade() {
    }

    public Localidade(String nome, String tipo, int geoCodigo, double latitude, double longitude, double altitude, Distrito distrito, Categoria categoria) {
        this.nome = nome;
        this.tipo = tipo;
        this.geoCodigo = geoCodigo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.distrito = distrito;
        this.categoria = categoria;
    }
    
    //gets
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getGeoCodigo() {
        return geoCodigo;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    
    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setGeoCodigo(int geoCodigo) {
        this.geoCodigo = geoCodigo;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
