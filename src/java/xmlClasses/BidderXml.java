/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement(name = "Bidder")
public class BidderXml {

    private String rating;
    private String userId;
    private String location;
    private String country;

    public String getRating() {
        return rating;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    @XmlAttribute(name = "Rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @XmlAttribute(name = "UserID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @XmlElement(name = "Location")
    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(name = "Country")
    public void setCountry(String country) {
        this.country = country;
    }

}
