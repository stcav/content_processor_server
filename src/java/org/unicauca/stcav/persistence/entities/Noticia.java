/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johaned
 */
@Entity
@Table(name = "Noticia", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Noticia.findAll", query = "SELECT n FROM Noticia n"),
    @NamedQuery(name = "Noticia.findByIdNoticia", query = "SELECT n FROM Noticia n WHERE n.idNoticia = :idNoticia"),
    @NamedQuery(name = "Noticia.findByComunidadidComunidad", query = "SELECT n FROM Noticia n WHERE n.comunidadidComunidad = :comunidadidComunidad"),
    @NamedQuery(name = "Noticia.findByIdnotificacion", query = "SELECT n FROM Noticia n WHERE n.idnotificacion = :idnotificacion"),
    @NamedQuery(name = "Noticia.findByTexto", query = "SELECT n FROM Noticia n WHERE n.texto = :texto"),
    @NamedQuery(name = "Noticia.findByTipoNoticiaidTipoNoticia", query = "SELECT n FROM Noticia n WHERE n.tipoNoticiaidTipoNoticia = :tipoNoticiaidTipoNoticia")})
public class Noticia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNoticia")
    private Long idNoticia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Comunidad_idComunidad")
    private long comunidadidComunidad;
    @Column(name = "Id_notificacion")
    private BigInteger idnotificacion;
    @Size(max = 255)
    @Column(name = "Texto")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tipo_Noticia_idTipo_Noticia")
    private long tipoNoticiaidTipoNoticia;

    public Noticia() {
    }

    public Noticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public Noticia(Long idNoticia, long comunidadidComunidad, long tipoNoticiaidTipoNoticia) {
        this.idNoticia = idNoticia;
        this.comunidadidComunidad = comunidadidComunidad;
        this.tipoNoticiaidTipoNoticia = tipoNoticiaidTipoNoticia;
    }

    public Long getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public BigInteger getIdnotificacion() {
        return idnotificacion;
    }

    public void setIdnotificacion(BigInteger idnotificacion) {
        this.idnotificacion = idnotificacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getTipoNoticiaidTipoNoticia() {
        return tipoNoticiaidTipoNoticia;
    }

    public void setTipoNoticiaidTipoNoticia(long tipoNoticiaidTipoNoticia) {
        this.tipoNoticiaidTipoNoticia = tipoNoticiaidTipoNoticia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNoticia != null ? idNoticia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Noticia)) {
            return false;
        }
        Noticia other = (Noticia) object;
        if ((this.idNoticia == null && other.idNoticia != null) || (this.idNoticia != null && !this.idNoticia.equals(other.idNoticia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.Noticia[ idNoticia=" + idNoticia + " ]";
    }
    
}
