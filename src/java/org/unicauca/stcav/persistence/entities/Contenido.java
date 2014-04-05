/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author johaned
 */
@Entity
@Table(name = "Contenido", catalog = "stcav1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contenido.findAll", query = "SELECT c FROM Contenido c"),
    @NamedQuery(name = "Contenido.findByIdContenido", query = "SELECT c FROM Contenido c WHERE c.idContenido = :idContenido"),
    @NamedQuery(name = "Contenido.findByDuracion", query = "SELECT c FROM Contenido c WHERE c.duracion = :duracion"),
    @NamedQuery(name = "Contenido.findByEstado", query = "SELECT c FROM Contenido c WHERE c.estado = :estado"),
    @NamedQuery(name = "Contenido.findByFechacreacion", query = "SELECT c FROM Contenido c WHERE c.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Contenido.findByFuente", query = "SELECT c FROM Contenido c WHERE c.fuente = :fuente"),
    @NamedQuery(name = "Contenido.findByTitulo", query = "SELECT c FROM Contenido c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Contenido.findByUsuarioidUsuario", query = "SELECT c FROM Contenido c WHERE c.usuarioidUsuario = :usuarioidUsuario")})
public class Contenido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idContenido")
    private Long idContenido;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Descriptor")
    private String descriptor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Duracion")
    private Double duracion;
    @Size(max = 13)
    @Column(name = "Estado")
    private String estado;
    @Column(name = "Fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Size(max = 255)
    @Column(name = "Fuente")
    private String fuente;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Ruta_fuente")
    private String rutafuente;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Ruta_screenshot")
    private String rutascreenshot;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Sinopsis")
    private String sinopsis;
    @Size(max = 255)
    @Column(name = "Titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private long usuarioidUsuario;

    public Contenido() {
    }

    public Contenido(Long idContenido) {
        this.idContenido = idContenido;
    }

    public Contenido(Long idContenido, long usuarioidUsuario) {
        this.idContenido = idContenido;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public Long getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Long idContenido) {
        this.idContenido = idContenido;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getRutafuente() {
        return rutafuente;
    }

    public void setRutafuente(String rutafuente) {
        this.rutafuente = rutafuente;
    }

    public String getRutascreenshot() {
        return rutascreenshot;
    }

    public void setRutascreenshot(String rutascreenshot) {
        this.rutascreenshot = rutascreenshot;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        hash += (idContenido != null ? idContenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenido)) {
            return false;
        }
        Contenido other = (Contenido) object;
        if ((this.idContenido == null && other.idContenido != null) || (this.idContenido != null && !this.idContenido.equals(other.idContenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.unicauca.stcav.persistence.entities.Contenido[ idContenido=" + idContenido + " ]";
    }
    
}
