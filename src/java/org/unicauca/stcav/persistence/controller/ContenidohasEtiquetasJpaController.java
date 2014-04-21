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
import org.unicauca.stcav.persistence.entities.ContenidohasEtiquetas;
import org.unicauca.stcav.persistence.entities.ContenidohasEtiquetasPK;

/**
 *
 * @author johaned
 */
public class ContenidohasEtiquetasJpaController implements Serializable {

    public ContenidohasEtiquetasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenidohasEtiquetas contenidohasEtiquetas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (contenidohasEtiquetas.getContenidohasEtiquetasPK() == null) {
            contenidohasEtiquetas.setContenidohasEtiquetasPK(new ContenidohasEtiquetasPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contenidohasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findContenidohasEtiquetas(contenidohasEtiquetas.getContenidohasEtiquetasPK()) != null) {
                throw new PreexistingEntityException("ContenidohasEtiquetas " + contenidohasEtiquetas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenidohasEtiquetas contenidohasEtiquetas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contenidohasEtiquetas = em.merge(contenidohasEtiquetas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContenidohasEtiquetasPK id = contenidohasEtiquetas.getContenidohasEtiquetasPK();
                if (findContenidohasEtiquetas(id) == null) {
                    throw new NonexistentEntityException("The contenidohasEtiquetas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ContenidohasEtiquetasPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenidohasEtiquetas contenidohasEtiquetas;
            try {
                contenidohasEtiquetas = em.getReference(ContenidohasEtiquetas.class, id);
                contenidohasEtiquetas.getContenidohasEtiquetasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenidohasEtiquetas with id " + id + " no longer exists.", enfe);
            }
            em.remove(contenidohasEtiquetas);
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

    public List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities() {
        return findContenidohasEtiquetasEntities(true, -1, -1);
    }

    public List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities(int maxResults, int firstResult) {
        return findContenidohasEtiquetasEntities(false, maxResults, firstResult);
    }

    private List<ContenidohasEtiquetas> findContenidohasEtiquetasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenidohasEtiquetas.class));
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

    public ContenidohasEtiquetas findContenidohasEtiquetas(ContenidohasEtiquetasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenidohasEtiquetas.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidohasEtiquetasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenidohasEtiquetas> rt = cq.from(ContenidohasEtiquetas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
