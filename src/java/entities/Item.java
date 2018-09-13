/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "item")
@NamedQueries({
    
    @NamedQuery(name = "Item.findByCategory",
            query = "SELECT i FROM Item i JOIN i.categories c "
            + "WHERE c.category = :category"),
    @NamedQuery(name = "Item.findByDescription",
            query = "SELECT i FROM Item i WHERE i.description LIKE :word"),
    @NamedQuery(name = "Item.findByPrice",
            query = "SELECT i FROM Item i WHERE i.buy_price <= :price"),
    @NamedQuery(name = "Item.findByLocation",
            query = "SELECT i FROM Item i WHERE i.location LIKE :location"),
    @NamedQuery(name = "Item.findItemsByCriterias",
            query = "SELECT i FROM Item i JOIN i.categories c "
            + "WHERE c.category = :category OR "
            + "i.description LIKE :description OR "
            + "i.buy_price <= :price OR "
            + "i.location LIKE :location"),
    @NamedQuery(name = "Item.findItems",
            query = "SELECT i FROM Item i")
})
public class Item implements Serializable, Comparator<Item>, Comparable<Item> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "item_category")
    private Collection<Category> categories;

    private double currently;
    private double buy_price;
    private double first_bid;
    private int number_of_bids;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<Bid> bids;

    private String location;
    private String country;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started",
            updatable = true,
            insertable = true,
            columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date started;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ends")
    private Date ends;

    @OneToOne(mappedBy = "item")
    @PrimaryKeyJoinColumn
    private Image image;

    @ManyToOne
    private Seller seller;

    private String status = "INACTIVE";

    @Column(columnDefinition = "Varchar(500)")
    private String description;

    public String _getForamtedStart() {
        String time = null;
        if (started != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            time = df.format(started);
        } else {
            System.out.println("Item: started == null");
        }
        return time;
    }

    public String _getForamtedEnd() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(ends);
    }

    public Image getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Category> getCategories() {
        return categories;
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

    public List<Bid> getBids() {
        return bids;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    public Date getStarted() {
        return started;
    }

    public Date getEnds() {
        return ends;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
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

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID:" + id + " Name:" + name; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(Item o1, Item o2) {
        return o1.ends.compareTo(o2.ends);
    }

    @Override
    public int compareTo(Item o) {
        return o.ends.compareTo(this.ends);
    }

}
