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
import org.unicauca.stcav.persistence.entities.ComunidadhasContenido;
import org.unicauca.stcav.persistence.entities.ComunidadhasContenidoPK;

/**
 *
 * @author johaned
 */
public class ComunidadhasContenidoJpaController implements Serializable {

    public ComunidadhasContenidoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComunidadhasContenido comunidadhasContenido) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (comunidadhasContenido.getComunidadhasContenidoPK() == null) {
            comunidadhasContenido.setComunidadhasContenidoPK(new ComunidadhasContenidoPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(comunidadhasContenido);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findComunidadhasContenido(comunidadhasContenido.getComunidadhasContenidoPK()) != null) {
                throw new PreexistingEntityException("ComunidadhasContenido " + comunidadhasContenido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComunidadhasContenido comunidadhasContenido) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            comunidadhasContenido = em.merge(comunidadhasContenido);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComunidadhasContenidoPK id = comunidadhasContenido.getComunidadhasContenidoPK();
                if (findComunidadhasContenido(id) == null) {
                    throw new NonexistentEntityException("The comunidadhasContenido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComunidadhasContenidoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ComunidadhasContenido comunidadhasContenido;
            try {
                comunidadhasContenido = em.getReference(ComunidadhasContenido.class, id);
                comunidadhasContenido.getComunidadhasContenidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunidadhasContenido with id " + id + " no longer exists.", enfe);
            }
            em.remove(comunidadhasContenido);
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

    public List<ComunidadhasContenido> findComunidadhasContenidoEntities() {
        return findComunidadhasContenidoEntities(true, -1, -1);
    }

    public List<ComunidadhasContenido> findComunidadhasContenidoEntities(int maxResults, int firstResult) {
        return findComunidadhasContenidoEntities(false, maxResults, firstResult);
    }

    private List<ComunidadhasContenido> findComunidadhasContenidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComunidadhasContenido.class));
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

    public ComunidadhasContenido findComunidadhasContenido(ComunidadhasContenidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComunidadhasContenido.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunidadhasContenidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComunidadhasContenido> rt = cq.from(ComunidadhasContenido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
