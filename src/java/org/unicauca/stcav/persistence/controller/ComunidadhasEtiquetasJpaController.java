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
import org.unicauca.stcav.persistence.entities.ComunidadhasEtiquetas;
import org.unicauca.stcav.persistence.entities.ComunidadhasEtiquetasPK;

/**
 *
 * @author johaned
 */
public class ComunidadhasEtiquetasJpaController implements Serializable {

    public ComunidadhasEtiquetasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComunidadhasEtiquetas comunidadhasEtiquetas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (comunidadhasEtiquetas.getComunidadhasEtiquetasPK() == null) {
            comunidadhasEtiquetas.setComunidadhasEtiquetasPK(new ComunidadhasEtiquetasPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comunidadhasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findComunidadhasEtiquetas(comunidadhasEtiquetas.getComunidadhasEtiquetasPK()) != null) {
                throw new PreexistingEntityException("ComunidadhasEtiquetas " + comunidadhasEtiquetas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComunidadhasEtiquetas comunidadhasEtiquetas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comunidadhasEtiquetas = em.merge(comunidadhasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComunidadhasEtiquetasPK id = comunidadhasEtiquetas.getComunidadhasEtiquetasPK();
                if (findComunidadhasEtiquetas(id) == null) {
                    throw new NonexistentEntityException("The comunidadhasEtiquetas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComunidadhasEtiquetasPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComunidadhasEtiquetas comunidadhasEtiquetas;
            try {
                comunidadhasEtiquetas = em.getReference(ComunidadhasEtiquetas.class, id);
                comunidadhasEtiquetas.getComunidadhasEtiquetasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunidadhasEtiquetas with id " + id + " no longer exists.", enfe);
            }
            em.remove(comunidadhasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
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

    public List<ComunidadhasEtiquetas> findComunidadhasEtiquetasEntities() {
        return findComunidadhasEtiquetasEntities(true, -1, -1);
    }

    public List<ComunidadhasEtiquetas> findComunidadhasEtiquetasEntities(int maxResults, int firstResult) {
        return findComunidadhasEtiquetasEntities(false, maxResults, firstResult);
    }

    private List<ComunidadhasEtiquetas> findComunidadhasEtiquetasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComunidadhasEtiquetas.class));
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

    public ComunidadhasEtiquetas findComunidadhasEtiquetas(ComunidadhasEtiquetasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComunidadhasEtiquetas.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunidadhasEtiquetasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComunidadhasEtiquetas> rt = cq.from(ComunidadhasEtiquetas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
