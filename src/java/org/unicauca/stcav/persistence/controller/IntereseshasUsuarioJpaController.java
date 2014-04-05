/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unicauca.stcav.persistence.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.unicauca.stcav.persistence.controller.exceptions.NonexistentEntityException;
import org.unicauca.stcav.persistence.controller.exceptions.PreexistingEntityException;
import org.unicauca.stcav.persistence.controller.exceptions.RollbackFailureException;
import org.unicauca.stcav.persistence.entities.IntereseshasUsuario;
import org.unicauca.stcav.persistence.entities.IntereseshasUsuarioPK;

/**
 *
 * @author johaned
 */
public class IntereseshasUsuarioJpaController implements Serializable {

    public IntereseshasUsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntereseshasUsuario intereseshasUsuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (intereseshasUsuario.getIntereseshasUsuarioPK() == null) {
            intereseshasUsuario.setIntereseshasUsuarioPK(new IntereseshasUsuarioPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(intereseshasUsuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findIntereseshasUsuario(intereseshasUsuario.getIntereseshasUsuarioPK()) != null) {
                throw new PreexistingEntityException("IntereseshasUsuario " + intereseshasUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntereseshasUsuario intereseshasUsuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            intereseshasUsuario = em.merge(intereseshasUsuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IntereseshasUsuarioPK id = intereseshasUsuario.getIntereseshasUsuarioPK();
                if (findIntereseshasUsuario(id) == null) {
                    throw new NonexistentEntityException("The intereseshasUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IntereseshasUsuarioPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            IntereseshasUsuario intereseshasUsuario;
            try {
                intereseshasUsuario = em.getReference(IntereseshasUsuario.class, id);
                intereseshasUsuario.getIntereseshasUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intereseshasUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(intereseshasUsuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntereseshasUsuario> findIntereseshasUsuarioEntities() {
        return findIntereseshasUsuarioEntities(true, -1, -1);
    }

    public List<IntereseshasUsuario> findIntereseshasUsuarioEntities(int maxResults, int firstResult) {
        return findIntereseshasUsuarioEntities(false, maxResults, firstResult);
    }

    private List<IntereseshasUsuario> findIntereseshasUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntereseshasUsuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public IntereseshasUsuario findIntereseshasUsuario(IntereseshasUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntereseshasUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntereseshasUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntereseshasUsuario> rt = cq.from(IntereseshasUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
