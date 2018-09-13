/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.BidderDao;
import entities.Bid;
import entities.Item;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class UserBidsBean {

    private List<Bid> bids;

    private List<Item> activeItems;
    private List<Item> expiredItems;

    @PostConstruct
    public void init() {

        Long user_id = (Long) SessionBean.getAttribute("user_id");
        BidderDao bidderDao = new BidderDao();
        bids = bidderDao.getBids(user_id);

        activeItems = new ArrayList<Item>();
        expiredItems = new ArrayList<Item>();
        for (Bid b : bids) {
            Item i = b.getItem();
            boolean hasEnded = i.getEnds().before(Date.from(Instant.now()));
            if (!hasEnded && !activeItems.contains(i)) {
                activeItems.add(i);
            } else if (hasEnded && !activeItems.contains(i)) {
                expiredItems.add(i);
            }
        }
        Collections.sort(activeItems);
        Collections.sort(expiredItems);
    }

    public double myLastBid(Long id) {
        Iterator<Bid> it = bids.listIterator();
        double current;
        current = 0;
        while (it.hasNext()) {
            Bid bid = it.next();
            if (bid.getItem().getId().equals(id) && bid.getAmount() > current) {
                current = bid.getAmount();
            }
        }
        return current;
    }

    /**
     * Creates a new instance of userBidsBean
     */
    public UserBidsBean() {
    }

    public List<Bid> getBids() {
        return bids;
    }

    public List<Item> getActiveItems() {
        return activeItems;
    }

    public List<Item> getExpiredItems() {
        return expiredItems;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setActiveItems(List<Item> activeItems) {
        this.activeItems = activeItems;
    }

    public void setExpiredItems(List<Item> expiredItems) {
        this.expiredItems = expiredItems;
    }

}
