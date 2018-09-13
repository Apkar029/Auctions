/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UserDao;
import entities.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean {

    @ManagedProperty(value = "#{id}", name = "user_id")
    private Long user_id;

    private User user;

    public void activateUser() {
        System.out.println("Activating User.");
        UserDao userDao = new UserDao();
        user.setStatus("ACTIVE");
        userDao.updateUser(user);
    }

    public void init() {
        UserDao userDao = new UserDao();
        user = userDao.findUserById(user_id);

    }

    public boolean isActive() {
        if (user != null) {
            if (user.getStatus().equals("ACTIVE")) {
                return true;
            }
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
