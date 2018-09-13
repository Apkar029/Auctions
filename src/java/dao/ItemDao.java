/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Category;
import entities.Image;
import entities.Item;
import entities.Item_;
import entities.Category_;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author Admin
 */
public class ItemDao {

    public void addItem(Item item) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(item);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: addItem");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteItem(Item item) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Item itemCopy = em.merge(item);
            Image image = itemCopy.getImage();
            if (image != null) {
                em.remove(image);
            }
            em.remove(itemCopy);
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: deleteItem");

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Item updateItem(Item item) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(item);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: updateItem");

        } finally {
            if (em != null) {
                em.close();
            }
        }
        return item;
    }

    public Item refreshItem(Item item) {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        Item itemManaged = null;
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            itemManaged = em.merge(item);
            em.refresh(itemManaged);
//            em.flush();
            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return itemManaged;
    }


    public Item findItemById(Long id) {
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        Item item = null;
        try {

            EntityTransaction tx = em.getTransaction();
            item = em.find(Item.class, id);
//            tx.begin();
//            Query q;
//            q = em.createNamedQuery("User.findByUsernameAndPassword");
//            q.setParameter("username", username);
//            q.setParameter("password", password);
//            List users = q.getResultList();
//            tx.commit();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: findItemById");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return item;
    }

    public List<Item> search(String query) {
        List<Item> items = new ArrayList<Item>();
        Set<Item> itemsSet = new HashSet<Item>();
        if (query != null && !query.equals("")) {
            String words[] = query.split(" ");
            EntityManagerFactory emf;
            emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
            EntityManager em = emf.createEntityManager();
            try {
                EntityTransaction tx = em.getTransaction();

                tx.begin();

                for (String word : words) {
                    System.out.println("WORD: " + word);
                    Query q;
                    q = em.createNamedQuery("Item.findByCategory");
                    q.setParameter("category", word);
                    List<Item> items_ = q.getResultList();
                    itemsSet.addAll(items_);

                    q = em.createNamedQuery("Item.findByDescription");
                    q.setParameter("word", "%" + word + "%");
                    items_ = q.getResultList();
                    itemsSet.addAll(items_);

                    try {
                        double price = Double.parseDouble(word);
                        q = em.createNamedQuery("Item.findByPrice");
                        q.setParameter("price", price);
                        items_ = q.getResultList();
                        itemsSet.addAll(items_);

                    } catch (NumberFormatException e) {

                    }
                    q = em.createNamedQuery("Item.findByLocation");
                    q.setParameter("location", "%" + word + "%");
                    items_ = q.getResultList();
                    itemsSet.addAll(items_);

                    items.addAll(itemsSet);

                    for (Item item : items) {
                        System.out.println(item.toString());
                    }
                }
                tx.commit();
            } catch (IllegalStateException e) {
                System.out.println("IllegalStateException: ItemDao: search");
            } finally {
                if (em.isOpen()) {
                    em.close();
                }
            }
        }
        return items;
    }

    public List<Item> findCategoryRange(int[] range, String category) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        List<Item> results = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            if (category != null && !category.equals("")) {
                CriteriaQuery<Item> q = cb.createQuery(Item.class);
                Root<Item> c = q.from(Item.class);
                Join<Item, Category> categories = c.join(Item_.categories);
                Predicate pred = cb.equal(categories.get(Category_.category), category);
                Predicate pred1 = cb.equal(c.get(Item_.status), "ACTIVE");
                Predicate pred2 = cb.greaterThan(c.<Date>get(Item_.ends), Date.from(Instant.now()));
                q.where(pred, pred1, pred2).distinct(true);
                TypedQuery<Item> query = em.createQuery(q);
                query.setMaxResults(range[1] - range[0]);
                query.setFirstResult(range[0]);
                results = query.getResultList();
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: findRange");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return results;
    }

    public List<Item> findRange(int[] range, String query_) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        List<Item> results = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            if (query_ != null && !query_.equals("")) {
                String words[] = query_.split(" ");
                CriteriaQuery<Item> q = cb.createQuery(Item.class
                );
                Root<Item> c = q.from(Item.class);
//        Path<String> description = c.get(Item_.description);
//                ParameterExpression<String> param = cb.parameter(String.class);
//                ParameterExpression<Double> param2 = cb.parameter(Double.class);
//                ParameterExpression<String> param3 = cb.parameter(String.class);

                Join<Item, Category> categories = c.join(Item_.categories);
//                Predicate descrRestr = cb.like(c.get(Item_.description), param);
//                Predicate priceRestr = cb.lessThan(c.get(Item_.buy_price), param2);
//                Predicate locatRestr = cb.like(c.get(Item_.location), param);
//                Predicate categRestr = cb.equal(categories.get(Category_.category), param3);

//            List<String> x = new ArrayList<String>();
//            x.add("sports");
//        x.add("clothing");
//            x.add("100");
//        p = cb.or(descrRestr, priceRestr, locatRestr, categRestr);
                List<Predicate> pred = new ArrayList<Predicate>();
                for (String word : words) {
                    try {
                        double price = Double.parseDouble(word);
                        pred.add(cb.lessThan(c.get(Item_.buy_price), price));
                    } catch (NumberFormatException e) {

                    } finally {
                        pred.add(cb.like(c.get(Item_.description), "%" + word + "%"));
                        pred.add(cb.like(c.get(Item_.location), "%" + word + "%"));
                        pred.add(cb.equal(categories.get(Category_.category), word));
                    }
                }
                Predicate p = cb.or(pred.toArray(new Predicate[pred.size()]));
                Predicate pred1 = cb.equal(c.get(Item_.status), "ACTIVE");
                Predicate pred2 = cb.greaterThan(c.<Date>get(Item_.ends), Date.from(Instant.now()));

                q.where(p, pred1, pred2)
                        .distinct(true);
                TypedQuery<Item> query = em.createQuery(q);

                query.setMaxResults(range[1] - range[0]);
                query.setFirstResult(range[0]);
                results = query.getResultList();

                System.out.println(query.unwrap(JpaQuery.class
                ).getDatabaseQuery().getSQLString());
                Iterator<Item> it = results.listIterator();

                while (it.hasNext()) {
                    Item i = it.next();
                    System.out.println(i.toString());
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: findRange");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return results;
    }

    public int categoryCount(String cateogry) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        List<Item> results = null;
        int size = 0;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            if (cateogry != null && !cateogry.equals("")) {
                CriteriaQuery<Item> q = cb.createQuery(Item.class);
                Root<Item> c = q.from(Item.class);
                Join<Item, Category> categories = c.join(Item_.categories);

                Predicate pred = cb.equal(categories.get(Category_.category), cateogry);

                Predicate pred1 = cb.equal(c.get(Item_.status), "ACTIVE");
                Predicate pred2 = cb.greaterThan(c.<Date>get(Item_.ends), Date.from(Instant.now()));

                q.where(pred, pred1, pred2)
                        .distinct(true);
                TypedQuery<Item> query = em.createQuery(q);

                results = query.getResultList();

                System.out.println(query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
                size = results.size();
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: count");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        System.out.println("ItemDao: count: " + size);
        return size;
    }

    public int count(String query_) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        List<Item> results = null;
        int size = 0;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            if (query_ != null && !query_.equals("")) {
                String words[] = query_.split(" ");
                CriteriaQuery<Item> q = cb.createQuery(Item.class
                );
                Root<Item> c = q.from(Item.class);

                ParameterExpression<String> param = cb.parameter(String.class);
                ParameterExpression<Double> param2 = cb.parameter(Double.class);
                ParameterExpression<String> param3 = cb.parameter(String.class);

                Join<Item, Category> categories = c.join(Item_.categories);
                Predicate descrRestr = cb.like(c.get(Item_.description), param);
                Predicate priceRestr = cb.lessThan(c.get(Item_.buy_price), param2);
                Predicate locatRestr = cb.like(c.get(Item_.location), param);
                Predicate categRestr = cb.equal(categories.get(Category_.category), param3);

                List<Predicate> pred = new ArrayList<Predicate>();
                for (String word : words) {
                    try {
                        double price = Double.parseDouble(word);
                        pred.add(cb.lessThan(c.get(Item_.buy_price), price));
                    } catch (NumberFormatException e) {

                    } finally {
                        pred.add(cb.like(c.get(Item_.description), "%" + word + "%"));
                        pred.add(cb.like(c.get(Item_.location), "%" + word + "%"));
                        pred.add(cb.equal(categories.get(Category_.category), word));
                    }
                }
                Predicate p = cb.or(pred.toArray(new Predicate[pred.size()]));
                Predicate pred1 = cb.equal(c.get(Item_.status), "ACTIVE");
                Predicate pred2 = cb.greaterThan(c.<Date>get(Item_.ends), Date.from(Instant.now()));

                q.where(p, pred1, pred2)
                        .distinct(true);
                TypedQuery<Item> query = em.createQuery(q);

                results = query.getResultList();

                System.out.println(query.unwrap(JpaQuery.class
                ).getDatabaseQuery().getSQLString());
                Iterator<Item> it = results.listIterator();

                while (it.hasNext()) {
                    Item i = it.next();
                    System.out.println(i.toString());
                }
                size = results.size();
            }
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: count");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        System.out.println("ItemDao: count: " + size);
        return size;
    }

    public List<Item> getAllItems(String query_) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
        EntityManager em = emf.createEntityManager();
        List<Item> results = null;
        int size = 0;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            if (query_ != null && !query_.equals("")) {
                String words[] = query_.split(" ");
                CriteriaQuery<Item> q = cb.createQuery(Item.class
                );
                Root<Item> c = q.from(Item.class);
//        Path<String> description = c.get(Item_.description);
                ParameterExpression<String> param = cb.parameter(String.class);
                ParameterExpression<Double> param2 = cb.parameter(Double.class);
                ParameterExpression<String> param3 = cb.parameter(String.class);

                Join<Item, Category> categories = c.join(Item_.categories);
                Predicate descrRestr = cb.like(c.get(Item_.description), param);
                Predicate priceRestr = cb.lessThan(c.get(Item_.buy_price), param2);
                Predicate locatRestr = cb.like(c.get(Item_.location), param);
                Predicate categRestr = cb.equal(categories.get(Category_.category), param3);

                List<String> x = new ArrayList<String>();

                x.add(
                        "sports");
//        x.add("clothing");
                x.add(
                        "100");
//        p = cb.or(descrRestr, priceRestr, locatRestr, categRestr);
                List<Predicate> pred = new ArrayList<Predicate>();
                for (String word : words) {
                    try {
                        double price = Double.parseDouble(word);
                        pred.add(cb.lessThan(c.get(Item_.buy_price), price));
                    } catch (NumberFormatException e) {

                    } finally {
                        pred.add(cb.like(c.get(Item_.description), "%" + word + "%"));
                        pred.add(cb.like(c.get(Item_.location), "%" + word + "%"));
                        pred.add(cb.equal(categories.get(Category_.category), word));
                    }
                }
                Predicate p = cb.or(pred.toArray(new Predicate[pred.size()]));

                q.where(p);
                TypedQuery<Item> query = em.createQuery(q);

                results = query.getResultList();

                System.out.println(query.unwrap(JpaQuery.class
                ).getDatabaseQuery().getSQLString());
                Iterator<Item> it = results.listIterator();

                while (it.hasNext()) {
                    Item i = it.next();
                    System.out.println(i.toString());
                }
                size = results.size();
            }

        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: ItemDao: getAllItems");
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return results;
    }
}
