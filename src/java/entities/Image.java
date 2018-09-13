/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "image")
public class Image implements Serializable {

    @Id
    @OneToOne
    private Item item;

    private String image;

    public Item getItem() {
        return item;
    }

    public String getImage() {
        return image;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
