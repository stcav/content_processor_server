/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "Valoracion", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracion.findAll", query = "SELECT v FROM Valoracion v"),
    @NamedQuery(name = "Valoracion.findByIdValoracion", query = "SELECT v FROM Valoracion v WHERE v.idValoracion = :idValoracion"),
    @NamedQuery(name = "Valoracion.findByEventoidEvento", query = "SELECT v FROM Valoracion v WHERE v.eventoidEvento = :eventoidEvento")})
public class Valoracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idValoracion")
    private Long idValoracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Evento_idEvento")
    private long eventoidEvento;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Texto")
    private String texto;

    public Valoracion() {
    }

    public Valoracion(Long idValoracion) {
        this.idValoracion = idValoracion;
    }

    public Valoracion(Long idValoracion, long eventoidEvento) {
        this.idValoracion = idValoracion;
        this.eventoidEvento = eventoidEvento;
    }

    public Long getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(Long idValoracion) {
        this.idValoracion = idValoracion;
    }

    public long getEventoidEvento() {
        return eventoidEvento;
    }

    public void setEventoidEvento(long eventoidEvento) {
        this.eventoidEvento = eventoidEvento;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValoracion != null ? idValoracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.idValoracion == null && other.idValoracion != null) || (this.idValoracion != null && !this.idValoracion.equals(other.idValoracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.Valoracion[ idValoracion=" + idValoracion + " ]";
    }
    
}
