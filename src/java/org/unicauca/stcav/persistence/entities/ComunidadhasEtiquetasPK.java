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
public class ComunidadhasEtiquetasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Etiquetas_idEtiquetas")
    private long etiquetasidEtiquetas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Comunidad_idComunidad")
    private long comunidadidComunidad;

    public ComunidadhasEtiquetasPK() {
    }

    public ComunidadhasEtiquetasPK(long etiquetasidEtiquetas, long comunidadidComunidad) {
        this.etiquetasidEtiquetas = etiquetasidEtiquetas;
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public long getEtiquetasidEtiquetas() {
        return etiquetasidEtiquetas;
    }

    public void setEtiquetasidEtiquetas(long etiquetasidEtiquetas) {
        this.etiquetasidEtiquetas = etiquetasidEtiquetas;
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
        hash += (int) etiquetasidEtiquetas;
        hash += (int) comunidadidComunidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasEtiquetasPK)) {
            return false;
        }
        ComunidadhasEtiquetasPK other = (ComunidadhasEtiquetasPK) object;
        if (this.etiquetasidEtiquetas != other.etiquetasidEtiquetas) {
            return false;
        }
        if (this.comunidadidComunidad != other.comunidadidComunidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.ComunidadhasEtiquetasPK[ etiquetasidEtiquetas=" + etiquetasidEtiquetas + ", comunidadidComunidad=" + comunidadidComunidad + " ]";
    }
    
}
