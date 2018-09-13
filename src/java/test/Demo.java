///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import entities.Bid;
//import entities.Bidder;
//import entities.Category;
//import entities.Item;
//import entities.Seller;
//import entities.Item_;
//import entities.Category_;
//import entities.User;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.ParameterExpression;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import org.eclipse.persistence.jpa.JpaQuery;
//import xmlClasses.BidXml;
//import xmlClasses.BidderXml;
//import xmlClasses.ItemXml;
//import xmlClasses.SellerXml;
//
//public class Demo {
//
//    public static void main(String args[]) {
//        User p = new User();
//        p.setUsername("John Rambo");
//        p.setPassword("Imakillya");
//
////        Item i = new Item();
////        Bid b = new Bid();
////        b.setItem(i);
//        Demo demo = new Demo();
////        demo.persist(i, b);
////        demo.createUser(3);
////        demo.createItem();
////        demo.bid(1, 2);
////        demo.getBids(1);
////        demo.play2();
//        demo.playXml();
//    }
//
//    public void getBids(long item_id) {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        Item item = em.find(Item.class, item_id);
//        List bids = item.getBids();
//
//        Iterator<Bid> it = bids.listIterator();
//
//        while (it.hasNext()) {
//            Bid bid = it.next();
//            Bidder bidder = bid.getBidder();
//            System.out.println("BID id:" + bid.getId()
//                    + "Bidder id:" + bidder.getUser_id());
//        }
//
//    }
//
//    public void createUser(long id) {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            User user = new User();
//            user.setUsername("John Rambo");
//            user.setPassword("Imakillya");
//            Seller seller = new Seller();
//            seller.setUser(user);
//            Bidder bidder = new Bidder();
//            bidder.setUser_id(user);
//            tx.begin();
//            em.persist(user);
//            em.persist(seller);
//            em.persist(bidder);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void createItem() {
//        Item item = new Item();
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        Seller seller = em.find(Seller.class, (long) 1);
//        item.setSeller(seller);
//        try {
//            tx.begin();
//            em.persist(item);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//
//    }
//
//    public void bid(long item_id, long bidder_id) {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        Bidder bidder = em.find(Bidder.class, bidder_id);
//        Item item = em.find(Item.class, item_id);
//        Bid bid = new Bid();
//        bid.setBidder(bidder);
//        bid.setItem(item);
//        try {
//            tx.begin();
//            em.persist(bid);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void persist(Object object, Object object2) {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            System.out.println("lalalala");
//            tx.begin();
//            em.persist(object);
//            tx.commit();
//            tx.begin();
//            em.persist(object2);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void remove(Object object) {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        try {
//            System.out.println("lalalala");
//            tx.begin();
//            Item object1;
//            Long id = new Long(2);
//            object1 = em.find(Item.class, id);
//            em.remove(object1);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void play() {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//        try {
//            System.out.println("lalalala");
//            Item object1;
//            Long id = (long) 3;
//            object1 = em.find(Item.class, id);
//            List bids = object1.getBids();
//            Iterator<Bid> it = bids.listIterator();
//            while (it.hasNext()) {
//                Bid b = it.next();
//                System.out.println(b.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
//
//    public void play2() {
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("TedAuctions_PU");
//        EntityManager em = emf.createEntityManager();
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//
//        CriteriaQuery<Item> q = cb.createQuery(Item.class);
//        Root<Item> c = q.from(Item.class);
////        Path<String> description = c.get(Item_.description);
//        ParameterExpression<String> param = cb.parameter(String.class);
//        ParameterExpression<Double> param2 = cb.parameter(Double.class);
//        ParameterExpression<String> param3 = cb.parameter(String.class);
//
//        Join<Item, Category> categories = c.join(Item_.categories);
//        Predicate descrRestr = cb.like(c.get(Item_.description), param);
//        Predicate priceRestr = cb.lessThan(c.get(Item_.buy_price), param2);
//        Predicate locatRestr = cb.like(c.get(Item_.location), param);
//        Predicate categRestr = cb.equal(categories.get(Category_.category), param3);
//
//        List<String> x = new ArrayList<String>();
//        x.add("sports");
////        x.add("clothing");
//        x.add("100");
////        p = cb.or(descrRestr, priceRestr, locatRestr, categRestr);
//        List<Predicate> pred = new ArrayList<Predicate>();
//        for (String s : x) {
//            try {
//                double price = Double.parseDouble(s);
//                pred.add(cb.lessThan(c.get(Item_.buy_price), price));
//            } catch (NumberFormatException e) {
//
//            } finally {
//                pred.add(cb.like(c.get(Item_.description), "%" + s + "%"));
//                pred.add(cb.like(c.get(Item_.location), "%" + s + "%"));
//                pred.add(cb.equal(categories.get(Category_.category), s));
//            }
//        }
////        Predicate p = cb.disjunction();
//        Predicate p = cb.or(pred.toArray(new Predicate[pred.size()]));
//        q.where(p);
//        TypedQuery<Item> query = em.createQuery(q);
//
////        TypedQuery<Item> query = em.createQuery(q);
////        for (String s : x) {
////            try {
////                double price = Double.parseDouble(s);
////                p = cb.or(descrRestr, priceRestr, locatRestr, categRestr);
////                q.where(descrRestr, priceRestr, locatRestr, categRestr);
////                query.setParameter(param, "%" + s + "%");
////                query.setParameter(param3, s);
////                query.setParameter(param2, price);
////            } catch (NumberFormatException e) {
////                p = cb.or(descrRestr, locatRestr, categRestr);
////                query.setParameter(param, "%clothing%");
////                query.setParameter(param2, 0.0);
////                query.setParameter(param3, s);
////            }
////        }
////        q.where(cb.or(categRestr, priceRestr));
////        query.setParameter(param, "%sports%");
////        query.setParameter(param2, 100.0);
//        query.setFirstResult(0);
//        query.setMaxResults(3);
//        List<Item> results = query.getResultList();
//        System.out.println(query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString());
//        Iterator<Item> it = results.listIterator();
//        while (it.hasNext()) {
//            Item i = it.next();
//            System.out.println(i.toString());
//        }
////        c.where(cb.or(
////                cb.like(Item_.description, param),
////                cb.like(Item_.description, param2)));
////        c = c.join(Category.class);
////        List<String> x = new ArrayList<String>();
////        x.add("sports");
////        x.add("clothing");
////        x.add("4000");
////        ParameterExpression<Integer> p = cb.parameter(Integer.class);
//        for (String s : x) {
////p=cb.and(cb.disjunction(c.get(""),s);
//        }
//        //        q.select(c).where(cb.gt(c.<Integer>get("buy_price"), p));
//        //        try {
//        //            EntityTransaction tx = em.getTransaction();
//        ////            tx.begin();
//        //            Query q;
//        //            q = em.createNamedQuery("Item.findItems");
//        //            List<Item> item = q.getResultList();
//        //            List<String> x = new ArrayList<String>();
//        //            x.add("sports");
//        //            x.add("clothing");
//        //            x.add("4000");
//        //            q = em.createQuery("SELECT distinct i FROM Item i JOIN Category c "
//        //                    + "WHERE (c.category in :list) or (i.location in :list)"
//        //                    + "or i.buy_price < in :list");
//        //            q.setParameter("list", x);
//        //            List<Item> ai = q.getResultList();
//        //
//        //            Iterator<Item> it = ai.listIterator();
//        //            System.out.println("lalalala");
//        //            while (it.hasNext()) {
//        //                Item i = it.next();
//        //                System.out.println(i.toString());
//        //            }
//        //
//        //        } catch (Exception e) {
//        //            e.printStackTrace();
//        //            em.getTransaction().rollback();
//        //        } finally {
//        //            em.close();
//        //        }
//    }
//
//    public void playXml() {
//
//        ItemXml item = new ItemXml();
//        BidderXml bidder = new BidderXml();
//        item.setName("Tommy Hilfiger jeans boy's 18-24 M (months)");
//        List<String> categories = new ArrayList();
//        categories.add("Clothing &amp; Accessories");
//        categories.add("Infants");
//        categories.add("Clothing");
//        categories.add("12-24 Months");
//        item.setCurrently("$7.50");
//        item.setFirst_bid("$7.00");
//        item.setNumber_of_bids(2);
//        List<BidXml> bids = new ArrayList<BidXml>();
//        BidXml bid1 = new BidXml();
//        BidderXml bidder1 = new BidderXml();
//        bidder1.setRating("229");
//        bidder1.setUserId("danobody");
//        bidder1.setLocation("Sydney");
//        bidder1.setCountry("Australia");
//        bid1.setBidder(bidder1);
//        bid1.setTime("Dec-10-01 08:21:26");
//        bid1.setAmount("$7.25");
//        bids.add(bid1);
//
//        BidXml bid2 = new BidXml();
//        BidderXml bidder2 = new BidderXml();
//        bidder2.setRating("1006");
//        bidder2.setUserId("dsage39564@aol.com");
//        bidder2.setLocation("In 'COACH' gift box");
//        bidder2.setCountry("USA");
//        bid2.setBidder(bidder2);
//        bid2.setTime("Dec-11-01 17:57:26");
//        bid2.setAmount("$7.50");
//        bids.add(bid2);
//
//        item.setBids(bids);
//
//        item.setLocation("JOHNNA'S QUALITY BARGAINS");
//        item.setCountry("USA");
//        item.setStarted("Dec-08-01 22:45:26");
//        item.setEnds("Dec-15-01 22:45:26");
//        SellerXml seller = new SellerXml();
//        seller.setRating("117");
//        seller.setUser_id("thewillsons-lufkin");
//
//        item.setSeller(seller);
//
//        item.setDescription("This is a really nice pair of Tommy Hilfiger \n"
//                + "denim jeans for little boy - 18-24 months. These were my son's \n"
//                + "jeans and he only wore them 2 times so they are like new!!! \n"
//                + "Jeans have two pockets in front and 2 pockets on rear. Hilfiger \n"
//                + "emblem is on front right pocket and on rear right pocket. Jeans \n"
//                + "have belt loops and snap up crotch for easy diaper changes. \n"
//                + "Really nice jeans - just like new!!! I think I paid around 35.00 \n"
//                + "for these at Dillard's. Buyer to pay 3.95 shipping. Insurance is \n"
//                + "available for 3.95. Seller is not responsible for packages once \n"
//                + "left at post office if uninsured. All sales are final. We prefer \n"
//                + "Paypal payments but we also accept m/o and cks. with 7-10 day \n"
//                + "hold on cks. Thanks for bidding and good luck!!! Merry \n"
//                + "Christmas!!! We ship on Wed. and Fri.");
//
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(ItemXml.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.marshal(item, new File("simple.xml"));
//            jaxbMarshaller.marshal(item, System.out);
//        } catch (JAXBException e) {
//            System.out.println("JAXBException");
//            e.printStackTrace();
//        }
//    }
//}
