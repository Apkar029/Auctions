/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.trash;

import beans.SessionBean;
import dao.BidderDao;
import entities.Bid;
import entities.Bidder;
import entities.Item;
import entities.Seller;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class UserPageBean implements Serializable {

    private List<Item> bidderItems;

    private List<Bid> bids;


    @PostConstruct
    public void init() {
        Long user_id = (Long) SessionBean.getAttribute("user_id");
        BidderDao bidderDao = new BidderDao();
        bids = bidderDao.getBids(user_id);
    }

    public List<Item> getBidderItems() {
        return bidderItems;
    }

    public Bid myLastBid(Long id) {
        Iterator<Bid> it = bids.listIterator();
        Bid current = new Bid();
        current.setAmount(0);
        while (it.hasNext()) {
            Bid bid = it.next();
            if (bid.getItem().getId().equals(id) && bid.getAmount() > current.getAmount()) {
                current = bid;
            }
        }
        return current;
    }

    public void setBidderItems(List<Item> bidderItems) {
        this.bidderItems = bidderItems;
    }

}
