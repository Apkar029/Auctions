/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.BidderDao;
import dao.SellerDao;
import dao.UserDao;
import entities.User;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {

    private String username;
    private String password;
    private String passwordCon;
    private String name;
    private String surname;
    private String email;
    private String phonenum;
    private String country;
    private String city;
    private String address;
    private String message;

    public void validatePasswords(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();

        UIInput uiPassword = (UIInput) components.findComponent("regForm:password");
        UIInput uiPasswordConf = (UIInput) components.findComponent("regForm:passwordCon");

        String passwordId = uiPassword.getClientId();
        String passwordConfId = uiPasswordConf.getClientId();
        if (uiPassword.getLocalValue() != null && uiPasswordConf.getLocalValue() != null) {
            String password_ = (String) uiPassword.getLocalValue().toString();
            String passwordConf_ = (String) uiPasswordConf.getLocalValue().toString();
            if (password_.length() < 6 || password_.length() > 16) {
                FacesMessage msg = new FacesMessage("Password must contain 6 to 16 characters.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(passwordId, msg);
                fc.renderResponse();
            }

            if (!password_.equals(passwordConf_)) {
                FacesMessage msg = new FacesMessage("Confirmation Password does not match");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(passwordConfId, msg);
                fc.renderResponse();
            }
        }
    }

    public String register() {

        UserDao userDao = new UserDao();

        if (userDao.findUserByUsername(username) == null) {

        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhonenum(phonenum);
        user.setCountry(country);
        user.setCity(city);
        user.setAddress(address);

        userDao.addUser(user);
        return "index.xhtml?faces-redirect=true";
    }

    public String getPasswordCon() {
        return passwordCon;
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

    public String getMessage() {
        return message;
    }

    public void setPasswordCon(String passwordCon) {
        this.passwordCon = passwordCon;
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

    public void setMessage(String message) {
        this.message = message;
    }

}
