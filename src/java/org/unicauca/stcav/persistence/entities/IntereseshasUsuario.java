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
@Table(name = "Intereses_has_Usuario", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntereseshasUsuario.findAll", query = "SELECT i FROM IntereseshasUsuario i"),
    @NamedQuery(name = "IntereseshasUsuario.findByUsuarioidUsuario", query = "SELECT i FROM IntereseshasUsuario i WHERE i.intereseshasUsuarioPK.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "IntereseshasUsuario.findByInteresesidIntereses", query = "SELECT i FROM IntereseshasUsuario i WHERE i.intereseshasUsuarioPK.interesesidIntereses = :interesesidIntereses")})
public class IntereseshasUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntereseshasUsuarioPK intereseshasUsuarioPK;

    public IntereseshasUsuario() {
    }

    public IntereseshasUsuario(IntereseshasUsuarioPK intereseshasUsuarioPK) {
        this.intereseshasUsuarioPK = intereseshasUsuarioPK;
    }

    public IntereseshasUsuario(long usuarioidUsuario, long interesesidIntereses) {
        this.intereseshasUsuarioPK = new IntereseshasUsuarioPK(usuarioidUsuario, interesesidIntereses);
    }

    public IntereseshasUsuarioPK getIntereseshasUsuarioPK() {
        return intereseshasUsuarioPK;
    }

    public void setIntereseshasUsuarioPK(IntereseshasUsuarioPK intereseshasUsuarioPK) {
        this.intereseshasUsuarioPK = intereseshasUsuarioPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intereseshasUsuarioPK != null ? intereseshasUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntereseshasUsuario)) {
            return false;
        }
        IntereseshasUsuario other = (IntereseshasUsuario) object;
        if ((this.intereseshasUsuarioPK == null && other.intereseshasUsuarioPK != null) || (this.intereseshasUsuarioPK != null && !this.intereseshasUsuarioPK.equals(other.intereseshasUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.IntereseshasUsuario[ intereseshasUsuarioPK=" + intereseshasUsuarioPK + " ]";
    }
    
}
