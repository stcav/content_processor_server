/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.unicauca.stcav.model;

/**
 *
 * @author JoGa
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class FileController{

    public FileController() {
    }

    /**
     * Copia todo el contenido de un directorio a otro directorio
     * @param srcDir
     * @param dstDir
     * @throws IOException
     */

    /**
     * Copia un solo archivo
     * @param s
     * @param t
     * @throws IOException
     */
    public void copyFile(File s, File t)
    {
        try{
              FileChannel in = (new FileInputStream(s)).getChannel();
              FileChannel out = (new FileOutputStream(t)).getChannel();
              in.transferTo(0, s.length(), out);
              in.close();
              out.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Copia un archivo y busca y sustituye un String dado
     * @param source_file
     * @param destination_file
     * @param toFind
     * @param toReplace
     * @throws IOException
     */
    public void copyFindAndReplace(String source_file, String destination_file, String toFind, String toReplace)
    {
        String str;
        try{
            FileInputStream fis2 = new FileInputStream(source_file);
            DataInputStream input = new DataInputStream (fis2);
            FileOutputStream fos2 = new FileOutputStream(destination_file);
            DataOutputStream output = new DataOutputStream (fos2);

            while (null != ((str = input.readLine())))
            {
                String s2=toFind;
                String s3=toReplace;

                int x=0;
                int y=0;
                String result="";
                while ((x=str.indexOf(s2, y))>-1) {
                    result+=str.substring(y,x);
                    result+=s3;
                    y=x+s2.length();
                }
                result+=str.substring(y);
                str=result;

                if(str.indexOf("'',") != -1){
                    continue;
                }
                else{
                    str=str+"\n";
                    output.writeBytes(str);
                }
            }
        }
        catch (IOException ioe)
        {
            System.err.println ("I/O Error - " + ioe);
        }
    }
}

