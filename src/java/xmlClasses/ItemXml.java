/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlClasses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Admin
 */
@XmlRootElement(name = "Item")
@XmlType(propOrder = {"id", "name", "categoty", "currently", "first_bid", "number_of_bids", "bids", "location", "country", "started", "ends", "seller", "description"})
public class ItemXml {

    Long id;
    String name;
    ArrayList<String> categoty;
    String currently;
    String first_bid;
    int number_of_bids;
    List<BidXml> bids;
    String location;
    String country;
    String started;
    String ends;
    SellerXml seller;
    String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCategoty() {
        return categoty;
    }

    public String getCurrently() {
        return currently;
    }

    public String getFirst_bid() {
        return first_bid;
    }

    public int getNumber_of_bids() {
        return number_of_bids;
    }

    public List<BidXml> getBids() {
        return bids;
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

    public SellerXml getSeller() {
        return seller;
    }

    public String getDescription() {
        return description;
    }

    @XmlAttribute(name = "ItemID")
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Category")
    public void setCategoty(ArrayList<String> categoty) {
        this.categoty = categoty;
    }

    @XmlElement(name = "Currently")
    public void setCurrently(String currently) {
        this.currently = currently;
    }

    @XmlElement(name = "First_Bid")
    public void setFirst_bid(String first_bid) {
        this.first_bid = first_bid;
    }

    @XmlElement(name = "Number_of_Bids")
    public void setNumber_of_bids(int number_of_bids) {
        this.number_of_bids = number_of_bids;
    }

    @XmlElement(name = "Bids")
    public void setBids(List<BidXml> bids) {
        this.bids = bids;
    }

    @XmlElement(name = "Location")
    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(name = "Country")
    public void setCountry(String country) {
        this.country = country;
    }

    @XmlElement(name = "Started")
    public void setStarted(String strarted) {
        this.started = strarted;
    }

    @XmlElement(name = "Ends")
    public void setEnds(String ends) {
        this.ends = ends;
    }

    @XmlElement(name = "Seller")
    public void setSeller(SellerXml seller) {
        this.seller = seller;
    }

    @XmlElement(name = "Description")
    public void setDescription(String description) {
        this.description = description;
    }

}
