/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.BidDao;
import dao.BidderDao;
import dao.ItemDao;
import dao.SellerDao;
import entities.Bid;
import entities.Bidder;
import entities.Item;
import entities.Seller;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import xmlClasses.ItemXml;
import xmlClasses.XmlHelper;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "itemBean")
@ViewScoped
public class ItemBean implements Serializable {

    private Long id;
    private Item item;
    private double amount;

    private String status;

    public String startAuction() {
        if (item != null) {
            item.setStatus("ACTIVE");
            ItemDao itemDao = new ItemDao();
            itemDao.updateItem(item);
            itemDao.refreshItem(item);
        } else {
            System.out.println("ItemBean: StartAuction: Item NULL!");
        }
        return "/itemPage.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public boolean hasStarted() {
        System.out.println("ItemBean: hasStarted: Status: " + item.getStatus());
        return item.getStatus().equals("ACTIVE");
    }

    public boolean hasEnded() {
        ItemDao itemDao = new ItemDao();
        Item item = itemDao.findItemById(id);
        if (item.getEnds() == null) {
            System.out.println("ENDS NULL!");
        }
        System.out.println("ItemBean: hasEnded: " + item.getEnds().before(Date.from(Instant.now())));
        return item.getEnds().before(Date.from(Instant.now()));
    }

    public void init() {
        System.out.println("ItemBean init: " + id);
        ItemDao itemDao = new ItemDao();
        if (id != null) {
            item = itemDao.findItemById(id);
        } else {
            item = new Item();
            System.out.println("ItemBean: init: item_id NULL!");
        }
    }

    public String deleteItem() {
        if (item != null) {
            ItemDao itemDao = new ItemDao();
            SellerDao sellerDao = new SellerDao();
            Seller seller = item.getSeller();
            itemDao.deleteItem(item);
            sellerDao.refreshSeller(seller);
        } else {
            System.out.println("ItemBean: DeleteItem: Item NULL!");
        }
        return "/restricted/userPage";
    }

    public void newBid() {
        System.out.println("newBid: id = " + id);
        String role = (String) SessionBean.getAttribute("role");
        if (!role.equals("guest")) {
            ItemDao itemDao = new ItemDao();
            BidderDao bidderDao = new BidderDao();
            BidDao bidDao = new BidDao();
            int bidNum = item.getNumber_of_bids();
            item.setNumber_of_bids(bidNum + 1);
            item.setCurrently(amount);
            itemDao.updateItem(item);
            Bid bid = new Bid();
            Bidder bidder = bidderDao.findBidderById((Long) SessionBean.getAttribute("user_id"));
            bid.setAmount(amount);
            bid.setBidder(bidder);
            bid.setItem(item);
            bidDao.addBid(bid);
            bidDao.refreshBid(bid);

            itemDao.refreshItem(item);
        }

    }

    public boolean uploadedByUser() {

        String role = (String) SessionBean.getAttribute("role");
        if (!role.equals("guest")) {
            System.out.println("ItemBean: uploadedByUser: " + item.getSeller().getUser_id().getId().equals((Long) SessionBean.getAttribute("user_id")));
            return item.getSeller().getUser_id().getId().equals((Long) SessionBean.getAttribute("user_id"));
        } else {
            return false;
        }
    }

    public void exportToXML() throws IOException {
        try {
            ItemXml itemXml = XmlHelper.exportXml(item);
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            OutputStream output = ec.getResponseOutputStream();

            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + itemXml.getName() + ".xml\"");
            JAXBContext jaxbContext = JAXBContext.newInstance(ItemXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(itemXml, output);
            jaxbMarshaller.marshal(itemXml, System.out);
            fc.responseComplete();

        } catch (JAXBException e) {
            System.out.println("ItemBean: exportToXML: JAXBException");
            e.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("ItemBean: exportToXML: IOException");

        }
    }

    public Long currentBuyerID() {
        return item.getBids().get(0).getBidder().getUser_id().getId();
    }

    public String _gereForamtedStart() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(item.getStarted());
    }

    public String _gereForamtedEnd() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df.format(item.getEnds());
    }

    public List<Bid> _getSortedBids() {
        System.out.println("ItemBean: getSortedBids");
        ItemDao itemDao = new ItemDao();
        List<Bid> bids = itemDao.findItemById(id).getBids();
        Collections.sort(bids);
        Collections.reverse(bids);
        return bids;
    }

    public String getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
