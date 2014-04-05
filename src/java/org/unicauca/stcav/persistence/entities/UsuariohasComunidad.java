/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "Usuario_has_Comunidad", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariohasComunidad.findAll", query = "SELECT u FROM UsuariohasComunidad u"),
    @NamedQuery(name = "UsuariohasComunidad.findByRol", query = "SELECT u FROM UsuariohasComunidad u WHERE u.rol = :rol"),
    @NamedQuery(name = "UsuariohasComunidad.findByUsuarioidUsuario", query = "SELECT u FROM UsuariohasComunidad u WHERE u.usuariohasComunidadPK.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "UsuariohasComunidad.findByComunidadidComunidad", query = "SELECT u FROM UsuariohasComunidad u WHERE u.usuariohasComunidadPK.comunidadidComunidad = :comunidadidComunidad")})
public class UsuariohasComunidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuariohasComunidadPK usuariohasComunidadPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Rol")
    private String rol;

    public UsuariohasComunidad() {
    }

    public UsuariohasComunidad(UsuariohasComunidadPK usuariohasComunidadPK) {
        this.usuariohasComunidadPK = usuariohasComunidadPK;
    }

    public UsuariohasComunidad(UsuariohasComunidadPK usuariohasComunidadPK, String rol) {
        this.usuariohasComunidadPK = usuariohasComunidadPK;
        this.rol = rol;
    }

    public UsuariohasComunidad(long usuarioidUsuario, long comunidadidComunidad) {
        this.usuariohasComunidadPK = new UsuariohasComunidadPK(usuarioidUsuario, comunidadidComunidad);
    }

    public UsuariohasComunidadPK getUsuariohasComunidadPK() {
        return usuariohasComunidadPK;
    }

    public void setUsuariohasComunidadPK(UsuariohasComunidadPK usuariohasComunidadPK) {
        this.usuariohasComunidadPK = usuariohasComunidadPK;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariohasComunidadPK != null ? usuariohasComunidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasComunidad)) {
            return false;
        }
        UsuariohasComunidad other = (UsuariohasComunidad) object;
        if ((this.usuariohasComunidadPK == null && other.usuariohasComunidadPK != null) || (this.usuariohasComunidadPK != null && !this.usuariohasComunidadPK.equals(other.usuariohasComunidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.UsuariohasComunidad[ usuariohasComunidadPK=" + usuariohasComunidadPK + " ]";
    }
    
}
