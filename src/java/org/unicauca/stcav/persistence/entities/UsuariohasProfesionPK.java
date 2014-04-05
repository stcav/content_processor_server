/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author johaned
 */
@Embeddable
public class UsuariohasProfesionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Profesion_idProfesion")
    private long profesionidProfesion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private long usuarioidUsuario;

    public UsuariohasProfesionPK() {
    }

    public UsuariohasProfesionPK(long profesionidProfesion, long usuarioidUsuario) {
        this.profesionidProfesion = profesionidProfesion;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getProfesionidProfesion() {
        return profesionidProfesion;
    }

    public void setProfesionidProfesion(long profesionidProfesion) {
        this.profesionidProfesion = profesionidProfesion;
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
        hash += (int) profesionidProfesion;
        hash += (int) usuarioidUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasProfesionPK)) {
            return false;
        }
        UsuariohasProfesionPK other = (UsuariohasProfesionPK) object;
        if (this.profesionidProfesion != other.profesionidProfesion) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.UsuariohasProfesionPK[ profesionidProfesion=" + profesionidProfesion + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
