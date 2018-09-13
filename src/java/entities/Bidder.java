/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "bidder")
public class Bidder implements Serializable {

    @Id
    @OneToOne
    private User user_id;

    private int rating;

    @OneToMany(mappedBy = "bidder")
    private List<Bid> bids;

    public User getUser_id() {
        return user_id;
    }

    public int getRating() {
        return rating;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setUser_id(User user) {
        this.user_id = user;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

}
