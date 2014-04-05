/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.unicauca.stcav.communication.RestClient;
import org.unicauca.stcav.jmx.mbean.DynamicMBeanFactory;
import org.unicauca.stcav.jmx.model.MyDynamicMBean;
import org.unicauca.stcav.model.Layout;

/**
 *
 * @author stcav
 */
public class Configurator {


    public static void main(String[] args) throws IOException {
        //Set MyDynamicMBeans names, remember to add xml descriptors to Mbean directory 
        List<String> labelLayout = new ArrayList();
        
        //note <id:name_mbean>
        labelLayout.add(Layout.REFERENCESESSION);
        labelLayout.add(Layout.CONTENTEDITING);
        labelLayout.add(Layout.CONTENTRECORD);
        labelLayout.add(Layout.CONTENTUPLOADING);
        
        RestClient rc = new RestClient();
        System.out.println(rc.post("http://localhost:19985/ContentProcessorServer/JMXProcessorServlet","data="+labelLayout.toString().replace("[", "").replace("]", "").replace(" ", "")));
        
    }

}
