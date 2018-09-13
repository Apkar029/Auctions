/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MessageDao;
import dao.UserDao;
import entities.Message;
import entities.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class MessageBean {

    private Message message;
    private Long message_id;
    private String messageBody;
    private Long from_id;
    private Long to_id;
    private String item;

    private String response = null;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    public void init() {
        if (message_id != null) {
            System.out.println("MessageBean: init: message_id: " + message_id);
            MessageDao messageDao = new MessageDao();
            message = messageDao.findMessageById(message_id);
            if (message.getStatus().equals("UNREAD")) {
                message.setStatus("READ");
                messageDao.updateMessage(message);
            }
            if (message == null) {
                System.out.println("MessageBean: init: message NULL");
            }
        } else if (from_id != null && to_id != null && item != null) {
            System.out.println("MessageBean: init: from_id: " + from_id);
            System.out.println("MessageBean: init: to_id: " + to_id);
            System.out.println("MessageBean: init: item: " + item);
        }
    }

    public void sendMessage() {
        MessageDao messageDao = new MessageDao();
        if (message_id != null) {
            message = messageDao.findMessageById(message_id);
            if (messageBody != null && message != null) {
                Message newMessage = new Message();
                newMessage.setItem(message.getItem());
                newMessage.setSender(message.getReceiver());
                newMessage.setReceiver(message.getSender());
                newMessage.setMessage(messageBody);
                messageDao.sentMessage(newMessage);
                messageDao.refreshMessage(newMessage);
            } else if (message == null) {
                System.out.println("MessageBean: sendMessage: message NULL");
            } else if (messageBody == null) {
                System.out.println("MessageBean: sendMessage: messageBody NULL");
            }
        } else {
            if (messageBody != null) {
                UserDao userDao = new UserDao();
                User sender = userDao.findUserById(from_id);
                User receiver = userDao.findUserById(to_id);
                message = new Message();
                message.setSender(sender);
                message.setReceiver(receiver);
                message.setItem(item);
                message.setMessage(messageBody);
                messageDao.sentMessage(message);
                messageDao.refreshMessage(message);
            }
        }
        response = "Message sent!";
    }

    public void sendMessage(Long from_id, Long to_id, String item) {
        MessageDao messageDao = new MessageDao();
        UserDao userDao = new UserDao();
        User form = userDao.findUserById(from_id);
        User to = userDao.findUserById(to_id);
        if (messageBody != null) {
            Message newMessage = new Message();
            newMessage.setItem(item);
            newMessage.setSender(form);
            newMessage.setReceiver(to);
            newMessage.setMessage(messageBody);
            messageDao.sentMessage(newMessage);
            messageDao.refreshMessage(newMessage);
        } else if (message == null) {
            System.out.println("MessageBean: sendMessage: message NULL");
        } else if (messageBody == null) {
            System.out.println("MessageBean: sendMessage: messageBody NULL");
        }
        response = "Message sent!";
    }

    public String getResponse() {
        return response;
    }

    public Long getFrom_id() {
        return from_id;
    }

    public Long getTo_id() {
        return to_id;
    }

    public String getItem() {
        return item;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setFrom_id(Long from_id) {
        this.from_id = from_id;
    }

    public void setTo_id(Long to_id) {
        this.to_id = to_id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
