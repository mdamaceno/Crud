/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Whereis;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
public class WhereisJpaController implements Serializable {

  public WhereisJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Whereis whereis) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      em.persist(whereis);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Whereis whereis) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      whereis = em.merge(whereis);
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = whereis.getId();
        if (findWhereis(id) == null) {
          throw new NonexistentEntityException("The whereis with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Integer id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Whereis whereis;
      try {
        whereis = em.getReference(Whereis.class, id);
        whereis.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The whereis with id " + id + " no longer exists.", enfe);
      }
      em.remove(whereis);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Whereis> findWhereisEntities() {
    return findWhereisEntities(true, -1, -1);
  }

  public List<Whereis> findWhereisEntities(int maxResults, int firstResult) {
    return findWhereisEntities(false, maxResults, firstResult);
  }

  private List<Whereis> findWhereisEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Whereis.class));
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

  public Whereis findWhereis(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Whereis.class, id);
    } finally {
      em.close();
    }
  }

  public int getWhereisCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Whereis> rt = cq.from(Whereis.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
