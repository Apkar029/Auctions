/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UserDao;
import entities.User;
import java.util.Enumeration;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

    private String password;
    private String username;

    private HttpSession session;

    public LoginBean() {
        session = SessionBean.getRequest().getSession(false);
        session.setAttribute("username", "guest");
        System.out.println("LoginBean: login: LoginBean: " + session.getAttribute("username"));

    }

    public String login() {
        String response = "/error/authenticationError.xhtml";
        UserDao userDao = new UserDao();
        User user = null;
        session = SessionBean.getSession(false);
//        System.out.println("LoginBean: login: username: " + session.getAttribute("username"));
        if ((user = userDao.validateUser(username, password)) != null) {
//            System.out.println("LoginBean: login: user_id: " + user.getId());
            if (user.getStatus().equals("ACTIVE")) {
                session.setAttribute("username", username);
                session.setAttribute("role", "user");
                session.setAttribute("user", user);
                session.setAttribute("user_id", user.getId());
                User userX = (User) session.getAttribute("user");
                System.out.println("LoginBean: login: username: " + session.getAttribute("username"));
                System.out.println("LoginBean: login: user_id: " + userX.getId());
                if (username.equals("admin") && password.equals("123456")) {
                    session.setAttribute("role", "admin");
                    System.out.println("LoginBean: login: username: " + session.getAttribute("username"));
                    response = "/restricted/admin.xhtml?faces-redirect=true";
                } else {
                    response = "/index.xhtml?faces-redirect=true";
                }
            }
        }
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            System.err.println("attr  = " + attr);
            Object value = session.getAttribute(attr);
            System.err.println("value = " + value);
        }
        return response;
    }

    public void validateUser(ComponentSystemEvent event) {
        UserDao userDao = new UserDao();
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();

        UIInput uiUsername = (UIInput) components.findComponent("username");
        UIInput uiPassword = (UIInput) components.findComponent("password");
        String passwordId = uiPassword.getClientId();
        System.out.println("LoginBea: validateUser: Username: " + uiUsername.getLocalValue());
        System.out.println("LoginBea: validateUser: Password: " + uiPassword.getLocalValue());
        String username_ = (String) uiUsername.getLocalValue().toString();
        String password_ = (String) uiPassword.getLocalValue().toString();
        if (userDao.validateUser(username_, password_) == null) {
            FacesMessage msg = new FacesMessage("Username and password do not match please try again");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();
        }
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

}
