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
import org.unicauca.stcav.persistence.entities.UsuariohasProfesion;
import org.unicauca.stcav.persistence.entities.UsuariohasProfesionPK;

/**
 *
 * @author johaned
 */
public class UsuariohasProfesionJpaController implements Serializable {

    public UsuariohasProfesionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuariohasProfesion usuariohasProfesion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuariohasProfesion.getUsuariohasProfesionPK() == null) {
            usuariohasProfesion.setUsuariohasProfesionPK(new UsuariohasProfesionPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(usuariohasProfesion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuariohasProfesion(usuariohasProfesion.getUsuariohasProfesionPK()) != null) {
                throw new PreexistingEntityException("UsuariohasProfesion " + usuariohasProfesion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuariohasProfesion usuariohasProfesion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            usuariohasProfesion = em.merge(usuariohasProfesion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuariohasProfesionPK id = usuariohasProfesion.getUsuariohasProfesionPK();
                if (findUsuariohasProfesion(id) == null) {
                    throw new NonexistentEntityException("The usuariohasProfesion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuariohasProfesionPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuariohasProfesion usuariohasProfesion;
            try {
                usuariohasProfesion = em.getReference(UsuariohasProfesion.class, id);
                usuariohasProfesion.getUsuariohasProfesionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariohasProfesion with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuariohasProfesion);
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

    public List<UsuariohasProfesion> findUsuariohasProfesionEntities() {
        return findUsuariohasProfesionEntities(true, -1, -1);
    }

    public List<UsuariohasProfesion> findUsuariohasProfesionEntities(int maxResults, int firstResult) {
        return findUsuariohasProfesionEntities(false, maxResults, firstResult);
    }

    private List<UsuariohasProfesion> findUsuariohasProfesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuariohasProfesion.class));
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

    public UsuariohasProfesion findUsuariohasProfesion(UsuariohasProfesionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuariohasProfesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariohasProfesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuariohasProfesion> rt = cq.from(UsuariohasProfesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
