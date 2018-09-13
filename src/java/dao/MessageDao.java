/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Message;
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
public class MessageDao {

    public void sentMessage(Message message) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(message);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: sentMessage");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteMessage(Message message) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Message messageCopy = em.merge(message);
            em.remove(messageCopy);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: deleteMessage");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Message updateMessage(Message message) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(message);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: updateMessage");

        } finally {
            if (em != null) {
                em.close();
            }
        }
        return message;
    }

    public Message refreshMessage(Message message) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        Message messageManaged = null;
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            messageManaged = em.merge(message);
            em.refresh(messageManaged);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: refreshMessage");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return messageManaged;
    }

    public Message findMessageById(Long message_id) {
        Message message = null;
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q = em.createNamedQuery("Message.findMsgsByID");
            q.setParameter("message_id", message_id);
            message = (Message) q.getSingleResult();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: findMessageById");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return message;
    }

    public List<Message> findAllMessages(Long user_id) {
        List<Message> messageList = null;
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q = em.createNamedQuery("Message.findMsgsByUserID");
            q.setParameter("user_id", user_id);
            messageList = q.getResultList();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: findAllMessages");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return messageList;
    }

    public List<Message> findMsgByReceiver(Long user_id) {
        List<Message> messageList = null;
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q = em.createNamedQuery("Message.findMsgsByReceiverID");
            q.setParameter("user_id", user_id);
            messageList = q.getResultList();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: findInboxMessages");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return messageList;
    }

    public List<Message> findMsgBySender(Long user_id) {
        List<Message> messageList = null;
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q = em.createNamedQuery("Message.findMsgsBySenderID");
            q.setParameter("user_id", user_id);
            messageList = q.getResultList();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: findInboxMessages");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return messageList;
    }

    public long getUnreadMessageCount(Long user_id) {
        long count = 0;
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query q = em.createNamedQuery("Message.getUnreadMsgCount");
            q.setParameter("user_id", user_id);
            count = (Long) q.getSingleResult();
            System.out.println("getUnreadMessageCount: " + count);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: MessageDao: getUnreadMessageCount");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return count;
    }
}
