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
public class IntereseshasUsuarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private long usuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Intereses_idIntereses")
    private long interesesidIntereses;

    public IntereseshasUsuarioPK() {
    }

    public IntereseshasUsuarioPK(long usuarioidUsuario, long interesesidIntereses) {
        this.usuarioidUsuario = usuarioidUsuario;
        this.interesesidIntereses = interesesidIntereses;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getInteresesidIntereses() {
        return interesesidIntereses;
    }

    public void setInteresesidIntereses(long interesesidIntereses) {
        this.interesesidIntereses = interesesidIntereses;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioidUsuario;
        hash += (int) interesesidIntereses;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntereseshasUsuarioPK)) {
            return false;
        }
        IntereseshasUsuarioPK other = (IntereseshasUsuarioPK) object;
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.interesesidIntereses != other.interesesidIntereses) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.IntereseshasUsuarioPK[ usuarioidUsuario=" + usuarioidUsuario + ", interesesidIntereses=" + interesesidIntereses + " ]";
    }
    
}
