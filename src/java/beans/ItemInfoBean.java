/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.ItemDao;
import entities.Category;
import entities.Item;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class ItemInfoBean {

    private String name;
    private double currently;
    private double buy_price;
    private double first_bid;
    private int number_of_bids;
    private String location;
    private String country;
    private String started;
    private String ends;
    private String description;
    private String categories;
    private String image;

    /**
     * Creates a new instance of ItemInfoBean
     */
    public ItemInfoBean() {
    }

    public void init(Long item_id) {
        if (item_id != null && item_id != 0) {//item_id!=0????????
            ItemDao itemDa0 = new ItemDao();
            Item item = itemDa0.findItemById(item_id);
            this.name = item.getName();
            this.currently = item.getCurrently();
            this.buy_price = item.getBuy_price();
            this.first_bid = item.getFirst_bid();
            this.number_of_bids = item.getNumber_of_bids();
            this.location = item.getLocation();
            this.country = item.getCountry();
            this.started = item._getForamtedStart();
            this.ends = item._getForamtedEnd();
            this.description = item.getDescription();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            if (item.getImage() != null) {
                this.image = "/data/" + item.getImage().getImage();
            } else {
                this.image = "/resources/images/screw.jpg";
            }

            Collection<Category> cat = (List) item.getCategories();
            Iterator<Category> it = cat.iterator();

            categories = it.next().getCategory();
            while (it.hasNext()) {
                Category temp = it.next();
                categories = categories.concat("," + temp.getCategory());
            }
        }
    }

    public String getImage() {
        return image;
    }

    public String getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getCurrently() {
        return currently;
    }

    public double getBuy_price() {
        return buy_price;
    }

    public double getFirst_bid() {
        return first_bid;
    }

    public int getNumber_of_bids() {
        return number_of_bids;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    public String getStarted() {
        return started;
    }

    public String getEnds() {
        return ends;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrently(double currently) {
        this.currently = currently;
    }

    public void setBuy_price(double buy_price) {
        this.buy_price = buy_price;
    }

    public void setFirst_bid(double first_bid) {
        this.first_bid = first_bid;
    }

    public void setNumber_of_bids(int number_of_bids) {
        this.number_of_bids = number_of_bids;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
