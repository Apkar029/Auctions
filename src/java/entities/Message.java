/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "message")
@NamedQueries({
    @NamedQuery(name = "Message.findMsgsByUserID",
            query = "SELECT m FROM Message m "
            + "WHERE m.sender.id = :user_id "
            + "OR m.receiver.id = :user_id"),
    @NamedQuery(name = "Message.getUnreadMsgCount",
            query = "SELECT COUNT(m.id) FROM Message m "
            + "WHERE m.receiver.id = :user_id "
            + "AND m.status = 'UNREAD'"),
    @NamedQuery(name = "Message.findMsgsBySenderID",
            query = "SELECT m FROM Message m "
            + "WHERE m.sender.id = :user_id "
            + "ORDER BY m.timeStamp DESC"),
    @NamedQuery(name = "Message.findMsgsByReceiverID",
            query = "SELECT m FROM Message m "
            + "WHERE m.receiver.id = :user_id "
            + "ORDER BY m.timeStamp DESC"),
    @NamedQuery(name = "Message.findMsgsByID",
            query = "SELECT m FROM Message m "
            + "WHERE m.id = :message_id")
})
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;

    private String item;
    private String message;

    @Column(name = "STATUS",
            insertable = false,
            updatable = true,
            columnDefinition = "VARCHAR(10) DEFAULT 'UNREAD'")
    private String status = "UNREAD";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESEND",
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timeStamp;

    public String _getForamtedTimeStamp() {
        String time = null;
        if (timeStamp != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            time = df.format(timeStamp);
        } else {
            System.out.println("Message: timeStamp == null");
        }
        return time;
    }

    public String getItem() {
        return item;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.message[ id=" + id + " ]";
    }

}
