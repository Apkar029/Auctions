/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Bid;
import entities.Bidder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class BidderDao {

    public void addBidder(Bidder bidder) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(bidder);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: BidderDao: addBidder");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteBidder(Bidder bidder) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(bidder);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: BidderDao: deleteBidder");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void refreshSeller(Bidder bidder) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Bidder sellerManaged = em.merge(bidder);
            em.refresh(sellerManaged);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bid> getBids(long user_id) {

        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        List<Bid> results = null;
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Bidder user = em.find(Bidder.class, user_id);
            results = user.getBids();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: BidderDao: getBids");

        } finally {
            if (em != null) {
                em.close();
            }
        }
        return results;
    }

    public Bidder findBidderById(Long id) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        Bidder bidder = null;
        try {
            EntityTransaction tx = em.getTransaction();
            bidder = em.find(Bidder.class, id);
//            tx.begin();
//            Query q;
//            q = em.createNamedQuery("User.findByUsernameAndPassword");
//            q.setParameter("username", username);
//            q.setParameter("password", password);
//            List users = q.getResultList();
//            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: SellerDao: findSellerById");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return bidder;
    }
}
