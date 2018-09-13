/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UserDao;
import entities.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "userProfileBean")
@RequestScoped
public class UserProfile {

    /**
     * Creates a new instance of UserProfile
     */
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
    private String status;

    public UserProfile() {
    }

    public void init(Long id) {
        UserDao userDao = new UserDao();
        User user = userDao.findUserById(id);
        if (user != null) {
            this.username = user.getUsername();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.email = user.getEmail();
            this.phonenum = user.getPhonenum();
            this.country = user.getCountry();
            this.city = user.getCity();
            this.address = user.getAddress();
            this.status = user.getStatus();
        }
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

}
