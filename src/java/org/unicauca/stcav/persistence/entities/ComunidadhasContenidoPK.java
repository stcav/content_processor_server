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
public class ComunidadhasContenidoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Contenido_idContenido")
    private long contenidoidContenido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Comunidad_idComunidad")
    private long comunidadidComunidad;

    public ComunidadhasContenidoPK() {
    }

    public ComunidadhasContenidoPK(long contenidoidContenido, long comunidadidComunidad) {
        this.contenidoidContenido = contenidoidContenido;
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public long getContenidoidContenido() {
        return contenidoidContenido;
    }

    public void setContenidoidContenido(long contenidoidContenido) {
        this.contenidoidContenido = contenidoidContenido;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contenidoidContenido;
        hash += (int) comunidadidComunidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasContenidoPK)) {
            return false;
        }
        ComunidadhasContenidoPK other = (ComunidadhasContenidoPK) object;
        if (this.contenidoidContenido != other.contenidoidContenido) {
            return false;
        }
        if (this.comunidadidComunidad != other.comunidadidComunidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.ComunidadhasContenidoPK[ contenidoidContenido=" + contenidoidContenido + ", comunidadidComunidad=" + comunidadidComunidad + " ]";
    }
    
}
