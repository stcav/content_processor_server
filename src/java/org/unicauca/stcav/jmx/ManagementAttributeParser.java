/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.jmx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author johaned
 */
public class ManagementAttributeParser {
    
    private static ManagementAttributeParser instance;
    private Map<String, String> descriptor;
    
     public static ManagementAttributeParser getInstance() throws UnknownHostException {
        if (instance == null) {
            instance = new ManagementAttributeParser();
        }
        return instance;
    }
     
     public ManagementAttributeParser(){
        InputStream yml = null;
        try {
            yml = new FileInputStream(new File(System.getProperty("user.dir")+"/mb_descriptor.yml"));
            Yaml yaml = new Yaml();
            descriptor = (Map<String, String>) yaml.load(yml);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManagementAttributeParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                yml.close();
            } catch (IOException ex) {
                Logger.getLogger(ManagementAttributeParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
     
     public String getManagementAttributeName(int key){
         return descriptor.get(key);
     }
    
}
