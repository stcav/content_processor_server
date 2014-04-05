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
@Table(name = "Comunidad_has_Etiquetas", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComunidadhasEtiquetas.findAll", query = "SELECT c FROM ComunidadhasEtiquetas c"),
    @NamedQuery(name = "ComunidadhasEtiquetas.findByEtiquetasidEtiquetas", query = "SELECT c FROM ComunidadhasEtiquetas c WHERE c.comunidadhasEtiquetasPK.etiquetasidEtiquetas = :etiquetasidEtiquetas"),
    @NamedQuery(name = "ComunidadhasEtiquetas.findByComunidadidComunidad", query = "SELECT c FROM ComunidadhasEtiquetas c WHERE c.comunidadhasEtiquetasPK.comunidadidComunidad = :comunidadidComunidad")})
public class ComunidadhasEtiquetas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComunidadhasEtiquetasPK comunidadhasEtiquetasPK;

    public ComunidadhasEtiquetas() {
    }

    public ComunidadhasEtiquetas(ComunidadhasEtiquetasPK comunidadhasEtiquetasPK) {
        this.comunidadhasEtiquetasPK = comunidadhasEtiquetasPK;
    }

    public ComunidadhasEtiquetas(long etiquetasidEtiquetas, long comunidadidComunidad) {
        this.comunidadhasEtiquetasPK = new ComunidadhasEtiquetasPK(etiquetasidEtiquetas, comunidadidComunidad);
    }

    public ComunidadhasEtiquetasPK getComunidadhasEtiquetasPK() {
        return comunidadhasEtiquetasPK;
    }

    public void setComunidadhasEtiquetasPK(ComunidadhasEtiquetasPK comunidadhasEtiquetasPK) {
        this.comunidadhasEtiquetasPK = comunidadhasEtiquetasPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comunidadhasEtiquetasPK != null ? comunidadhasEtiquetasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasEtiquetas)) {
            return false;
        }
        ComunidadhasEtiquetas other = (ComunidadhasEtiquetas) object;
        if ((this.comunidadhasEtiquetasPK == null && other.comunidadhasEtiquetasPK != null) || (this.comunidadhasEtiquetasPK != null && !this.comunidadhasEtiquetasPK.equals(other.comunidadhasEtiquetasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.ComunidadhasEtiquetas[ comunidadhasEtiquetasPK=" + comunidadhasEtiquetasPK + " ]";
    }
    
}
