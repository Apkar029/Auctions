/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Category;
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
public class CategoryDao {

    public List<String> getAllCategories() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q;
        q = em.createNamedQuery("Category.findAll");
        List<String> categories = q.getResultList();
        tx.commit();
        em.close();

        
        return categories;
    }

    public Category findCategoryByName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query q;
        q = em.createNamedQuery("Category.findCategoryByName");
        q.setParameter("category", name);
        List<Category> c = q.getResultList();
        tx.commit();
        Category category = c.get(0);
        em.close();
        return category;
    }

}
