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
import model.DeliveryMen;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
public class DeliveryMenJpaController implements Serializable {

  public DeliveryMenJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(DeliveryMen deliveryMen) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      em.persist(deliveryMen);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(DeliveryMen deliveryMen) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      deliveryMen = em.merge(deliveryMen);
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = deliveryMen.getId();
        if (findDeliveryMen(id) == null) {
          throw new NonexistentEntityException("The deliveryMen with id " + id + " no longer exists.");
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
      DeliveryMen deliveryMen;
      try {
        deliveryMen = em.getReference(DeliveryMen.class, id);
        deliveryMen.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The deliveryMen with id " + id + " no longer exists.", enfe);
      }
      em.remove(deliveryMen);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<DeliveryMen> findDeliveryMenEntities() {
    return findDeliveryMenEntities(true, -1, -1);
  }

  public List<DeliveryMen> findDeliveryMenEntities(int maxResults, int firstResult) {
    return findDeliveryMenEntities(false, maxResults, firstResult);
  }

  private List<DeliveryMen> findDeliveryMenEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(DeliveryMen.class));
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

  public DeliveryMen findDeliveryMen(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(DeliveryMen.class, id);
    } finally {
      em.close();
    }
  }

  public int getDeliveryMenCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<DeliveryMen> rt = cq.from(DeliveryMen.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
