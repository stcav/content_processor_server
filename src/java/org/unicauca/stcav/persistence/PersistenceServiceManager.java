/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author johaned
 * 
 */
public class PersistenceServiceManager {
    @Resource private UserTransaction utx; 
    private EntityManagerFactory emf;
    
    private PersistenceServiceManager() {
        this.emf = Persistence.createEntityManagerFactory("ContentProcessorServerPU");
    }
    
    public static PersistenceServiceManager getInstance() {
        return PersistenceServiceManagerHolder.INSTANCE;
    }
    
    private static class PersistenceServiceManagerHolder {

        private static final PersistenceServiceManager INSTANCE = new PersistenceServiceManager();
    }
    
    public UserTransaction get_utx(){
        return utx;
    }
    
    public EntityManagerFactory get_emf(){
        return emf;
    }
}
