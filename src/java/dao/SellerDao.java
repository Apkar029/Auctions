/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Item;
import entities.Seller;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
public class SellerDao {

    public void addSeller(Seller seller) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(seller);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteSeller(Seller seller) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(seller);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateSeller(Seller seller) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(seller);
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

    public void refreshSeller(Seller seller) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Seller sellerManaged = em.merge(seller);
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

    public List<Item> getSellerItems(Long user_id) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        List<Item> itemList = null;
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Seller seller = em.find(Seller.class, user_id);
            itemList = seller.getItems();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: SellerDao: getSellerItems");

        } finally {
            if (em != null) {
                em.close();
            }
        }
        return itemList;
    }

    public Seller findSellerById(Long id) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        Seller seller = null;
        try {
            EntityTransaction tx = em.getTransaction();
            seller = em.find(Seller.class, id);
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
        return seller;
    }
}
