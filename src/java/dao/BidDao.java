/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Bid;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class BidDao {

    public void addBid(Bid bid) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(bid);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: BidDao: addBid");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Bid refreshBid(Bid bid) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        Bid bidManaged = null;
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            bidManaged = em.merge(bid);
            em.refresh(bidManaged);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return bidManaged;
    }
}
