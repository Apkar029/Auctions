/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.SellerDao;
import entities.Item;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class userOffersBean {

    private List<Item> items;

    private List<Item> activeItems;
    private List<Item> inactiveItems;
    private List<Item> expiredItems;

    /**
     * Creates a new instance of userOffersBean
     */
    public userOffersBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("UserOffersBean: init");
        SellerDao sellerDao = new SellerDao();
        Long user_id = (Long) SessionBean.getAttribute("user_id");
        items = sellerDao.getSellerItems(user_id);

        activeItems = new ArrayList<Item>();
        inactiveItems = new ArrayList<Item>();
        expiredItems = new ArrayList<Item>();
        for (Item i : items) {
            boolean hasEnded = i.getEnds().before(Date.from(Instant.now()));
            if (i.getStatus().equals("ACTIVE") && !hasEnded) {
                activeItems.add(i);
            } else if (i.getStatus().equals("INACTIVE") && !hasEnded) {
                inactiveItems.add(i);
            } else if (hasEnded) {
                expiredItems.add(i);
            }
        }

        Collections.sort(activeItems);
        Collections.sort(inactiveItems);
        Collections.sort(expiredItems);

    }

    public List<Item> getActiveItems() {
        return activeItems;
    }

    public List<Item> getInactiveItems() {
        return inactiveItems;
    }

    public List<Item> getExpiredItems() {
        return expiredItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setActiveItems(List<Item> activeItems) {
        this.activeItems = activeItems;
    }

    public void setInactiveItems(List<Item> inactiveItems) {
        this.inactiveItems = inactiveItems;
    }

    public void setExpiredItems(List<Item> expiredItems) {
        this.expiredItems = expiredItems;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
