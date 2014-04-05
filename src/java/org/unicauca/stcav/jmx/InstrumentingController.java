/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.jmx;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.unicauca.stcav.jmx.model.MbeanAbstract;
import org.unicauca.stcav.jmx.model.MyDynamicMBean;

/**
 *
 * @author stcav
 */
public class InstrumentingController {

    private static InstrumentingController instance;
    private List<MbeanAbstract> mbas;

    private InstrumentingController() {
        mbas = new ArrayList<MbeanAbstract>();
    }

    public static InstrumentingController getInstance() throws UnknownHostException {
        if (instance == null) {
            instance = new InstrumentingController();
        }
        return instance;
    }

    public List<MbeanAbstract> getMbas() {
        return mbas;
    }
    
    public void set_myDynamicMBean(String label, MyDynamicMBean mdmb){
        mbas.add(new MbeanAbstract(mdmb, label));
    }
    
    public MyDynamicMBean find_myDynamicMBean(String label){
        for (Iterator<MbeanAbstract> it = mbas.iterator(); it.hasNext();) {
            MbeanAbstract mbeanAbstract = it.next();
            if(mbeanAbstract.get_label().equals(label)){
                return mbeanAbstract.getMdmb();
            }
        }
        return null;
    }

    
    
}
