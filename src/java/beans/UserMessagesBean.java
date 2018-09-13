/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MessageDao;
import entities.Message;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class UserMessagesBean {

    private List<Message> inboxMessages;
    private List<Message> sentMessages;

    @PostConstruct
    public void init() {
        Long user_id = (Long) SessionBean.getAttribute("user_id");
        MessageDao messageDao = new MessageDao();
        inboxMessages = messageDao.findMsgByReceiver(user_id);
        sentMessages = messageDao.findMsgBySender(user_id);
    }

    public UserMessagesBean() {
    }

    public List<Message> getInboxMessages() {
        return inboxMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setInboxMessages(List<Message> inboxMessages) {
        this.inboxMessages = inboxMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

}
