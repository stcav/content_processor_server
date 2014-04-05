/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.model;

/**
 *
 * @author Johan Tique
 */
public class BackAnswer {

    public static int SUCESSFUL = 0,  ERROR = 1;
    private int estado;
    private String dato;
    private String descripcion;

    public BackAnswer() {
    }

    public BackAnswer(int estado, String dato, String descripcion) {
        this.estado = estado;
        this.dato = dato;
        this.descripcion = descripcion;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
