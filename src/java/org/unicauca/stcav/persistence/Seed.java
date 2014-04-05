/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence;

import org.unicauca.stcav.persistence.controller.UsuarioJpaController;
import org.unicauca.stcav.persistence.entities.Usuario;

/**
 *
 * @author johaned
 */
public class Seed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PersistenceServiceManager psm = PersistenceServiceManager.getInstance();
        UsuarioJpaController ujc = new UsuarioJpaController(psm.get_utx(), psm.get_emf());
        System.out.println("The initial user aready exists!");
        System.out.println(ujc.findUsuarioEntities());
        //Usuario administrador = new Usuario();
        /*if (ujc.findUsuario(1L) != null) {
            administrador.setIdUsuario(1L);
            administrador.setNombres("Admin");
            administrador.setLogin("admin");
            administrador.setPwd("123");
            administrador.setGenero("m");
            administrador.setEmail("admin@test.com");
            administrador.setDireccion("Fake St. No 12 - 3");
            ujc.create(administrador);
        } else {
            System.out.println("The initial user aready exists!");
        }*/



    }
}
