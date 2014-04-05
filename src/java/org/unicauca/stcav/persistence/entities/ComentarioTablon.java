/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johaned
 */
@Entity
@Table(name = "Comentario_Tablon", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComentarioTablon.findAll", query = "SELECT c FROM ComentarioTablon c"),
    @NamedQuery(name = "ComentarioTablon.findByIdComentarioTablon", query = "SELECT c FROM ComentarioTablon c WHERE c.idComentarioTablon = :idComentarioTablon"),
    @NamedQuery(name = "ComentarioTablon.findByEstampa", query = "SELECT c FROM ComentarioTablon c WHERE c.estampa = :estampa"),
    @NamedQuery(name = "ComentarioTablon.findByMensaje", query = "SELECT c FROM ComentarioTablon c WHERE c.mensaje = :mensaje"),
    @NamedQuery(name = "ComentarioTablon.findByTablonidTablon", query = "SELECT c FROM ComentarioTablon c WHERE c.tablonidTablon = :tablonidTablon"),
    @NamedQuery(name = "ComentarioTablon.findByUsuarioidUsuario", query = "SELECT c FROM ComentarioTablon c WHERE c.usuarioidUsuario = :usuarioidUsuario")})
public class ComentarioTablon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComentario_Tablon")
    private Long idComentarioTablon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Estampa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estampa;
    @Size(max = 255)
    @Column(name = "Mensaje")
    private String mensaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tablon_idTablon")
    private long tablonidTablon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private long usuarioidUsuario;

    public ComentarioTablon() {
    }

    public ComentarioTablon(Long idComentarioTablon) {
        this.idComentarioTablon = idComentarioTablon;
    }

    public ComentarioTablon(Long idComentarioTablon, Date estampa, long tablonidTablon, long usuarioidUsuario) {
        this.idComentarioTablon = idComentarioTablon;
        this.estampa = estampa;
        this.tablonidTablon = tablonidTablon;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public Long getIdComentarioTablon() {
        return idComentarioTablon;
    }

    public void setIdComentarioTablon(Long idComentarioTablon) {
        this.idComentarioTablon = idComentarioTablon;
    }

    public Date getEstampa() {
        return estampa;
    }

    public void setEstampa(Date estampa) {
        this.estampa = estampa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getTablonidTablon() {
        return tablonidTablon;
    }

    public void setTablonidTablon(long tablonidTablon) {
        this.tablonidTablon = tablonidTablon;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentarioTablon != null ? idComentarioTablon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComentarioTablon)) {
            return false;
        }
        ComentarioTablon other = (ComentarioTablon) object;
        if ((this.idComentarioTablon == null && other.idComentarioTablon != null) || (this.idComentarioTablon != null && !this.idComentarioTablon.equals(other.idComentarioTablon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.ComentarioTablon[ idComentarioTablon=" + idComentarioTablon + " ]";
    }
    
}
