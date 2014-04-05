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
public class PreferenciasProgramasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Programa_idPrograma")
    private long programaidPrograma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private long usuarioidUsuario;

    public PreferenciasProgramasPK() {
    }

    public PreferenciasProgramasPK(long programaidPrograma, long usuarioidUsuario) {
        this.programaidPrograma = programaidPrograma;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getProgramaidPrograma() {
        return programaidPrograma;
    }

    public void setProgramaidPrograma(long programaidPrograma) {
        this.programaidPrograma = programaidPrograma;
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
        hash += (int) programaidPrograma;
        hash += (int) usuarioidUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciasProgramasPK)) {
            return false;
        }
        PreferenciasProgramasPK other = (PreferenciasProgramasPK) object;
        if (this.programaidPrograma != other.programaidPrograma) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.PreferenciasProgramasPK[ programaidPrograma=" + programaidPrograma + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
