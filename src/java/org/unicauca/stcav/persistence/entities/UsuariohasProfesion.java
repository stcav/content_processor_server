/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johaned
 */
@Entity
@Table(name = "Usuario_has_Profesion", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariohasProfesion.findAll", query = "SELECT u FROM UsuariohasProfesion u"),
    @NamedQuery(name = "UsuariohasProfesion.findByProfesionidProfesion", query = "SELECT u FROM UsuariohasProfesion u WHERE u.usuariohasProfesionPK.profesionidProfesion = :profesionidProfesion"),
    @NamedQuery(name = "UsuariohasProfesion.findByUsuarioidUsuario", query = "SELECT u FROM UsuariohasProfesion u WHERE u.usuariohasProfesionPK.usuarioidUsuario = :usuarioidUsuario")})
public class UsuariohasProfesion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuariohasProfesionPK usuariohasProfesionPK;

    public UsuariohasProfesion() {
    }

    public UsuariohasProfesion(UsuariohasProfesionPK usuariohasProfesionPK) {
        this.usuariohasProfesionPK = usuariohasProfesionPK;
    }

    public UsuariohasProfesion(long profesionidProfesion, long usuarioidUsuario) {
        this.usuariohasProfesionPK = new UsuariohasProfesionPK(profesionidProfesion, usuarioidUsuario);
    }

    public UsuariohasProfesionPK getUsuariohasProfesionPK() {
        return usuariohasProfesionPK;
    }

    public void setUsuariohasProfesionPK(UsuariohasProfesionPK usuariohasProfesionPK) {
        this.usuariohasProfesionPK = usuariohasProfesionPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuariohasProfesionPK != null ? usuariohasProfesionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasProfesion)) {
            return false;
        }
        UsuariohasProfesion other = (UsuariohasProfesion) object;
        if ((this.usuariohasProfesionPK == null && other.usuariohasProfesionPK != null) || (this.usuariohasProfesionPK != null && !this.usuariohasProfesionPK.equals(other.usuariohasProfesionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.UsuariohasProfesion[ usuariohasProfesionPK=" + usuariohasProfesionPK + " ]";
    }
    
}
