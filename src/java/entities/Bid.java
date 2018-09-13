/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "bid")
public class Bid implements Serializable, Comparator<Bid>, Comparable<Bid> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bidTime",
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timeStamp;

    private double amount;

    @ManyToOne
    private Item item;

    @ManyToOne
    @MapKeyColumn(name = "BIDDER_USER")
    private Bidder bidder;

    public String _gereForamtedTimeStamp() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(timeStamp);
    }

    public Long getId() {
        return id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    @Override
    public String toString() {
        return "Bid=[" + id + "," + timeStamp.toString() + "," + amount + "]"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Bid o) {
        return Double.compare(this.amount, o.amount);
    }

    @Override
    public int compare(Bid o1, Bid o2) {
        return Double.compare(o1.amount, o2.amount);
    }

}
