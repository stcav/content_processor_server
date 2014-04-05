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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johaned
 */
@Entity
@Table(name = "Preferencias_Programas", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreferenciasProgramas.findAll", query = "SELECT p FROM PreferenciasProgramas p"),
    @NamedQuery(name = "PreferenciasProgramas.findByFavorito", query = "SELECT p FROM PreferenciasProgramas p WHERE p.favorito = :favorito"),
    @NamedQuery(name = "PreferenciasProgramas.findByRecordatorio", query = "SELECT p FROM PreferenciasProgramas p WHERE p.recordatorio = :recordatorio"),
    @NamedQuery(name = "PreferenciasProgramas.findByProgramaidPrograma", query = "SELECT p FROM PreferenciasProgramas p WHERE p.preferenciasProgramasPK.programaidPrograma = :programaidPrograma"),
    @NamedQuery(name = "PreferenciasProgramas.findByUsuarioidUsuario", query = "SELECT p FROM PreferenciasProgramas p WHERE p.preferenciasProgramasPK.usuarioidUsuario = :usuarioidUsuario")})
public class PreferenciasProgramas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciasProgramasPK preferenciasProgramasPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Favorito")
    private short favorito;
    @Column(name = "Recordatorio")
    private Short recordatorio;

    public PreferenciasProgramas() {
    }

    public PreferenciasProgramas(PreferenciasProgramasPK preferenciasProgramasPK) {
        this.preferenciasProgramasPK = preferenciasProgramasPK;
    }

    public PreferenciasProgramas(PreferenciasProgramasPK preferenciasProgramasPK, short favorito) {
        this.preferenciasProgramasPK = preferenciasProgramasPK;
        this.favorito = favorito;
    }

    public PreferenciasProgramas(long programaidPrograma, long usuarioidUsuario) {
        this.preferenciasProgramasPK = new PreferenciasProgramasPK(programaidPrograma, usuarioidUsuario);
    }

    public PreferenciasProgramasPK getPreferenciasProgramasPK() {
        return preferenciasProgramasPK;
    }

    public void setPreferenciasProgramasPK(PreferenciasProgramasPK preferenciasProgramasPK) {
        this.preferenciasProgramasPK = preferenciasProgramasPK;
    }

    public short getFavorito() {
        return favorito;
    }

    public void setFavorito(short favorito) {
        this.favorito = favorito;
    }

    public Short getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(Short recordatorio) {
        this.recordatorio = recordatorio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferenciasProgramasPK != null ? preferenciasProgramasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciasProgramas)) {
            return false;
        }
        PreferenciasProgramas other = (PreferenciasProgramas) object;
        if ((this.preferenciasProgramasPK == null && other.preferenciasProgramasPK != null) || (this.preferenciasProgramasPK != null && !this.preferenciasProgramasPK.equals(other.preferenciasProgramasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.PreferenciasProgramas[ preferenciasProgramasPK=" + preferenciasProgramasPK + " ]";
    }
    
}
