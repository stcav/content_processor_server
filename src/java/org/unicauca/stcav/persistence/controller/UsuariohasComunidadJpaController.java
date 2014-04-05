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
import org.unicauca.stcav.persistence.entities.UsuariohasComunidad;
import org.unicauca.stcav.persistence.entities.UsuariohasComunidadPK;

/**
 *
 * @author johaned
 */
public class UsuariohasComunidadJpaController implements Serializable {

    public UsuariohasComunidadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuariohasComunidad usuariohasComunidad) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuariohasComunidad.getUsuariohasComunidadPK() == null) {
            usuariohasComunidad.setUsuariohasComunidadPK(new UsuariohasComunidadPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(usuariohasComunidad);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuariohasComunidad(usuariohasComunidad.getUsuariohasComunidadPK()) != null) {
                throw new PreexistingEntityException("UsuariohasComunidad " + usuariohasComunidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuariohasComunidad usuariohasComunidad) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            usuariohasComunidad = em.merge(usuariohasComunidad);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuariohasComunidadPK id = usuariohasComunidad.getUsuariohasComunidadPK();
                if (findUsuariohasComunidad(id) == null) {
                    throw new NonexistentEntityException("The usuariohasComunidad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuariohasComunidadPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuariohasComunidad usuariohasComunidad;
            try {
                usuariohasComunidad = em.getReference(UsuariohasComunidad.class, id);
                usuariohasComunidad.getUsuariohasComunidadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariohasComunidad with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuariohasComunidad);
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

    public List<UsuariohasComunidad> findUsuariohasComunidadEntities() {
        return findUsuariohasComunidadEntities(true, -1, -1);
    }

    public List<UsuariohasComunidad> findUsuariohasComunidadEntities(int maxResults, int firstResult) {
        return findUsuariohasComunidadEntities(false, maxResults, firstResult);
    }

    private List<UsuariohasComunidad> findUsuariohasComunidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuariohasComunidad.class));
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

    public UsuariohasComunidad findUsuariohasComunidad(UsuariohasComunidadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuariohasComunidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariohasComunidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuariohasComunidad> rt = cq.from(UsuariohasComunidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
