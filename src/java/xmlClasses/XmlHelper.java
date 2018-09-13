/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlClasses;

import entities.Bid;
import entities.Bidder;
import entities.Category;
import entities.Item;
import entities.Seller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Admin
 */
public class XmlHelper {

    public static ItemXml exportXml(Item item) {

        ItemXml itemXml = new ItemXml();
        itemXml.setName(item.getName());

        List<String> categoriesXml = new ArrayList();
        List<Category> categories = (List<Category>) item.getCategories();
        for (Category c : categories) {
            categoriesXml.add(c.getCategory());
        }

//        categories.add("Clothing &amp; Accessories");
//        categories.add("Infants");
//        categories.add("Clothing");
//        categories.add("12-24 Months");
        itemXml.setCurrently("$" + item.getCurrently());
        itemXml.setFirst_bid("$" + item.getFirst_bid());
        itemXml.setNumber_of_bids(item.getNumber_of_bids());

        List<BidXml> bidsXml = new ArrayList<BidXml>();
        List<Bid> bids = item.getBids();
        for (Bid b : bids) {
            Bidder bidder = b.getBidder();
            BidderXml bidderXml = new BidderXml();
            bidderXml.setRating(Integer.toString(bidder.getRating()));
            bidderXml.setUserId(Long.toString(bidder.getUser_id().getId()));
            bidderXml.setLocation(bidder.getUser_id().getCity() + " " + bidder.getUser_id().getAddress());
            bidderXml.setCountry(bidder.getUser_id().getCountry());
            BidXml bidXml = new BidXml();
            bidXml.setTime(b._gereForamtedTimeStamp());
            bidXml.setAmount("$" + b.getAmount());
            bidXml.setBidder(bidderXml);
            bidsXml.add(bidXml);
        }

        itemXml.setBids(bidsXml);
        itemXml.setLocation(item.getLocation());
        itemXml.setCountry(item.getCountry());
        itemXml.setStarted(item._getForamtedStart());
        itemXml.setEnds(item._getForamtedEnd());

        SellerXml sellerXml = new SellerXml();
        sellerXml.setRating(Integer.toString(item.getSeller().getRating()));
        sellerXml.setUser_id(Long.toString(item.getSeller().getUser_id().getId()));

        itemXml.setSeller(sellerXml);

        itemXml.setDescription(item.getDescription());
        return itemXml;
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(ItemXml.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.marshal(itemXml, new File("C:\\XMLADMIN\\" + item.getName() + ".xml"));
//            jaxbMarshaller.marshal(itemXml, System.out);
//        } catch (JAXBException e) {
//            System.out.println("JAXBException");
//            e.printStackTrace();
//        }
    }
}
