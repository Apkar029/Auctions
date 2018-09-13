/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Bidder;
import entities.Seller;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class UserDao {

    public void addUser(User user) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            Seller seller = new Seller();
            Bidder bidder = new Bidder();
            seller.setUser(user);
            bidder.setUser_id(user);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(user);
            em.persist(seller);
            em.persist(bidder);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: UserDao: addUser");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteUser(User user) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(user);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: UserDao: deleteUser");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateUser(User user) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(user);
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


    public User findUserById(Long id) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {
            EntityTransaction tx = em.getTransaction();
            user = em.find(User.class, id);
//            tx.begin();
//            Query q;
//            q = em.createNamedQuery("User.findByUsernameAndPassword");
//            q.setParameter("username", username);
//            q.setParameter("password", password);
//            List users = q.getResultList();
//            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: UserDao: findUserById");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return user;
    }

    public User findUserByUsername(String username) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {
            EntityTransaction tx = em.getTransaction();
//            user = em.find(User.class, id);
            tx.begin();
            Query q;
            q = em.createNamedQuery("User.findByUsername");
            q.setParameter("username", username);
//            q.setParameter("password", password);
            List users = q.getResultList();
            tx.commit();
            if (users != null && users.size() == 1) {
                user = (User) users.get(0);
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: UserDao: findUserByUsername");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return user;
    }

    public User validateUser(String username, String password) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {

            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q;
            q = em.createNamedQuery("User.findByUsernameAndPassword");
            q.setParameter("username", username);
            q.setParameter("password", password);
            List users = q.getResultList();
            tx.commit();
            if (users != null && users.size() == 1) {
                user = (User) users.get(0);
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: UserDao: validateUser");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return user;
    }

}
