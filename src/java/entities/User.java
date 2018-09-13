/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByUsernameAndPassword", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"), //    @NamedQuery(name = "User.findPendingUsers", query = "SELECT u FROM User u,PendingUser p WHERE u.id = p.id")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phonenum;
    private String country;
    private String city;
    private String address;

    @OneToMany(mappedBy = "sender")
    private Collection<Message> sendMessages;
    @OneToMany(mappedBy = "receiver")
    private Collection<Message> receivedMessages;

    @Column(name = "status",
            columnDefinition = "Varchar(40) default 'PENDING' NOT NULL",
            insertable = false)
    private String status = "PENDING";

    @OneToOne(mappedBy = "user_id")
    @PrimaryKeyJoinColumn
    private Seller seller;

    @OneToOne(mappedBy = "user_id")
    @PrimaryKeyJoinColumn
    private Bidder bidder;

    public boolean active() {
        return status.equals("ACTIVE");
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public Seller getSeller() {
        return seller;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public Collection<Message> getSendMessages() {
        return sendMessages;
    }

    public Collection<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setSendMessages(Collection<Message> sendMessages) {
        this.sendMessages = sendMessages;
    }

    public void setReceivedMessages(Collection<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }

}
