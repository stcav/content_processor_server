/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author johan
 */
public class FileProcessor {

    public static void do_file_write(String name, String line) {
        //nombre es la ruta y nombre del archivo sobre el que deseamos trabajar
        //linea es la cadena que deseamos introducir en nuestro archivo
        File archivo = new File(name);
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            //true para no modificar lo que ya estaba en el archivo, false para hacer lo contrario
            BufferedWriter buffer = new BufferedWriter(escribirArchivo);
            buffer.write(line);
            buffer.newLine();
            buffer.close();//buena practica cerrar para liberar memoria
        } catch (Exception ex) {
            System.out.println("Error:" + ex);/*en caso de haber error aqui se mostrarÃ¡;
            aunque puedes mostrarlo con una ventana emergente o como quieras*/
        }
    }

    public static String leerArchivo(String name) {
        //nombre es la ruta y nombre del archivo sobre el que deseamos trabajar
        String returned = "";
        String line = "";
        File archivo = new File(name);
        try {
            FileReader leerArchivo = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leerArchivo);
            while ((line = buffer.readLine()) != null) {
                returned += line + "\n";
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error:" + ex);/*en caso de haber error aqui se mostrarÃ¡;
            aunque puedes mostrarlo con una ventana emergente o como quieras*/
        }
        return returned;
    }
}
