/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
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
@Table(name = "seller")
public class Seller implements Serializable {

    @Id
    @OneToOne
    private User user_id;

    @Column
    private int rating = 0;

    @OneToMany(mappedBy = "seller")
    private List<Item> items;

    public User getUser_id() {
        return user_id;
    }

    public int getRating() {
        return rating;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setUser(User user) {
        this.user_id = user;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
