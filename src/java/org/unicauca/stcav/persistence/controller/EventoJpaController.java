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
import org.unicauca.stcav.persistence.controller.exceptions.RollbackFailureException;
import org.unicauca.stcav.persistence.entities.Evento;

/**
 *
 * @author johaned
 */
public class EventoJpaController implements Serializable {

    public EventoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evento evento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(evento);
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

    public void edit(Evento evento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            evento = em.merge(evento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = evento.getIdEvento();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getIdEvento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            em.remove(evento);
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

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evento.class));
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

    public Evento findEvento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evento> rt = cq.from(Evento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
