/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlClasses;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement(name = "Bid")
public class BidXml {

    private BidderXml bidder;
    private String time;
    private String amount;

    public BidderXml getBidder() {
        return bidder;
    }

    public String getTime() {
        return time;
    }

    public String getAmount() {
        return amount;
    }

    @XmlElement(name = "Bidder")
    public void setBidder(BidderXml bidder) {
        this.bidder = bidder;
    }

    @XmlElement(name = "Time")
    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement(name = "Amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
