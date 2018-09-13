/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MessageDao;
import dao.UserDao;
import entities.User;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private String username = null;
    private String password;
    private User current = null;
    private int errorFlag = 0;
    private long id = 0;

    private long unreadMsgCount = 0;

    private LinkedList<String> history = new LinkedList<String>();

    private String lastPage;

    public SessionBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) getSession(true);

        session.setAttribute("role", "guest");
    }

    public void addToHistory() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
//        String viewId = facesContext.getViewRoot().getViewId();
        HttpServletRequest servletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String viewId = servletRequest.getRequestURL().toString();
        System.out.println("SessionBean: addToHistory: viewId: " + viewId);
        history.push(viewId);
    }

    public String removeFromHistory() {
        String viewId = history.pop();
        System.out.println("SessionBean: removeFromHistory: viewId: " + viewId);
        return history.element();
    }

    public long getUnreadMsgCount() {
        String role = (String) SessionBean.getAttribute("role");
        if (!role.equals("guest")) {
            MessageDao messageDao = new MessageDao();
            Long user_id = (Long) SessionBean.getAttribute("user_id");
            unreadMsgCount = messageDao.getUnreadMessageCount(user_id);
        }
        return unreadMsgCount;
    }

    public void refreshDB() {
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("TedAuctions_PU");
        em = emf.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        em.close();
    }

    public boolean isLoggedIn() {
        HttpSession session = SessionBean.getSession(false);
        System.out.println("SessionBean: isLoggedIn: role: " + session.getAttribute("role"));
        return !session.getAttribute("role").equals("guest");
    }

    public String logout() {
        if (current != null) {
            current = null;
        }
        HttpSession session = SessionBean.getSession(false);
        session.invalidate();
        username = null;
        password = null;

        return "/index.xhtml?faces-redirect=true";
    }

    public void validateUser(ComponentSystemEvent event) {
        UserDao userDao = new UserDao();
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();

        UIInput uiUsername = (UIInput) components.findComponent("username");
        UIInput uiPassword = (UIInput) components.findComponent("password");
        String passwordId = uiPassword.getClientId();
        System.out.println("Username: " + uiUsername.getLocalValue());
        System.out.println("Password: " + uiPassword.getLocalValue());
        if (uiUsername.getLocalValue() != null && uiPassword.getLocalValue() != null) {
            String username_ = (String) uiUsername.getLocalValue().toString();
            String password_ = (String) uiPassword.getLocalValue().toString();
            if (userDao.validateUser(username_, password_) == null) {
                FacesMessage msg = new FacesMessage("Username and password do not match please try again");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(passwordId, msg);
                fc.renderResponse();
            }
        }
    }

    public static HttpSession getSession(boolean param) {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(param);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static Object getAttribute(String att) {
        return getSession(false).getAttribute(att);
    }

    public static void setAttribute(String att, Object value) {
        HttpSession session = getSession(false);
        session.setAttribute(att, value);
    }

    public static String getUser_id() {
        HttpSession session = getSession(false);
        if (session != null) {
            return (String) session.getAttribute("user_id");
        } else {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getCurrent() {
        return current;
    }

    public int getErrorFlag() {
        return errorFlag;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public void setErrorFlag(int errorFlag) {
        this.errorFlag = errorFlag;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LinkedList getHistory() {
        return history;
    }

    public void setLastPage(String navigationCase) {
        history.push(navigationCase);
        if (history.size() > 10) {
            history.pollLast();
        }
    }

    public String getLastPage() {
        return history.pop();
    }

}
