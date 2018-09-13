/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement(name = "Seller")
public class SellerXml {

    private String rating;
    private String user_id;

    public String getRating() {
        return rating;
    }

    public String getUser_id() {
        return user_id;
    }

    @XmlAttribute(name = "Rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @XmlAttribute(name = "UserID")
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
