/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.CategoryDao;
import dao.ItemDao;
import dao.SellerDao;
import dao.UserDao;
import static com.sun.faces.facelets.util.Path.context;
import dao.ImageDao;
import entities.Category;
import entities.Image;
import entities.Item;
import entities.Seller;
import entities.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@ManagedBean
@ViewScoped
public class NewOfferBean implements Serializable {

    private Long id;
    private String name;
    private double buy_price;
    private double first_bid;
    private String location;
    private String ends;
    private String description;

    private List<Category> categories;
    private List<String> categories_;

    private boolean err = false;

    private Part file;

    private List<Part> files;

    private Long user_id;

    public void checkOffer() {
        if (id != null) {
            ItemDao itemDao = new ItemDao();

            Item item = itemDao.findItemById(id);
            this.name = item.getName();
            this.buy_price = item.getBuy_price();
            this.first_bid = item.getFirst_bid();
            this.location = item.getLocation();
            this.ends = item._getForamtedEnd();
            this.categories = (List) item.getCategories();
            this.description = item.getDescription();
            this.categories = (List) item.getCategories();
        }
        categories_ = new CategoryDao().getAllCategories();

    }

    public void changeErr() {
        System.out.println(err);
        err = err != true;
        System.out.println(err);

    }

    public String saveOffer() throws ParseException {

        String categories_temp = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("hidden-tags");

        ItemDao itemDao = new ItemDao();
        SellerDao sellerDao = new SellerDao();
        err = true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(ends, formatter);
        Date e = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);

        System.out.println(ends);
        System.out.println(e);
        Item item;
        if (id != null) {
            item = itemDao.findItemById(id);
        } else {
            item = new Item();
        }
        if (e.after(Date.from(Instant.now()))) {
            item.setName(name);
            item.setBuy_price(buy_price);
            item.setFirst_bid(first_bid);
            item.setCurrently(first_bid);
            item.setLocation(location);
            item.setDescription(description);
            item.setStarted(null);
            item.setEnds(e);
            Seller seller = sellerDao.findSellerById((Long) SessionBean.getAttribute("user_id"));
            item.setSeller(seller);
            sellerDao.refreshSeller(seller);

            System.out.println("Categories: " + categories_temp);
            String l[] = categories_temp.split(",");
            CategoryDao categoryDao = new CategoryDao();
            List<Category> _categories = new ArrayList<Category>();
            for (String l1 : l) {
                Category category = categoryDao.findCategoryByName(l1);
                _categories.add(category);
            }

            item.setCategories(_categories);

            if (id == null) {
                itemDao.addItem(item);
                item = itemDao.refreshItem(item);
                id = item.getId();
            } else {
                itemDao.updateItem(item);
                itemDao.refreshItem(item);

            }
            System.out.println("NewOfferBean: saveOffer: ite_id: " + id);
            if (file != null) {

                ImageDao imageDao = new ImageDao();
                Image image = new Image();
                try {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    System.out.println("absoluteWebPath: " + absoluteWebPath);
                    System.out.println("contentType: " + file.getContentType());
                    if (file.getContentType().contains("jpg") && file.getContentType().contains("png")) {
                        new File(absoluteWebPath + "\\data\\").mkdir();

                        file.write(absoluteWebPath + "\\data\\" + getFilename(file));
                        image.setImage(getFilename(file));
                        item = itemDao.findItemById(id);
                        image.setItem(item);
                        if (item.getImage() == null) {
                            imageDao.addImage(image);
                        } else {
                            imageDao.updateImage(image);
                        }
                    }
                } catch (IOException ex) {
                    // Error handling
                }

            }
        }
        return "/itemPage.xhtml?faces-redirect=true&includeViewParams=true";

    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public void validateImage(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiImage = (UIInput) components.findComponent("file");
        String imageId = uiImage.getClientId();
        Part image = (Part) uiImage.getLocalValue();
        if (image != null && image.getSize() / (1024 * 1024) > 4) {
            FacesMessage msg = new FacesMessage("Image size too big");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(imageId, msg);
            fc.renderResponse();
        }
    }

    public void validatePrices(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();

        UIInput uiBuy_price = (UIInput) components.findComponent("buy_price");
        UIInput uiFirst_bid = (UIInput) components.findComponent("first_bid");
        String first_bidId = uiFirst_bid.getClientId();
        String buy_priceId = uiBuy_price.getClientId();
        if (uiBuy_price.getLocalValue() != null && uiFirst_bid.getLocalValue() != null
                && Double.parseDouble(uiBuy_price.getLocalValue().toString()) > 0
                && Double.parseDouble(uiFirst_bid.getLocalValue().toString()) > 0) {
            double buy_price_ = Double.parseDouble(uiBuy_price
                    .getLocalValue().toString());
            double first_bid_ = Double.parseDouble(uiFirst_bid
                    .getLocalValue().toString());
            System.out.println("Buy price: " + buy_price_);
            System.out.println("First bid: " + first_bid_);
            if (Double.compare(buy_price_, first_bid_) < 0) {
                FacesMessage msg = new FacesMessage("First bid must be lower than buying price");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(first_bidId, msg);
                fc.renderResponse();
            }
        } else {
            if (uiBuy_price.getLocalValue() == null
                    || Double.parseDouble(uiBuy_price.getLocalValue().toString()) == 0.0) {

                FacesMessage msg1 = new FacesMessage("Buy price must be greater than zero");
                msg1.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(buy_priceId, msg1);
                fc.renderResponse();
            }
            if (uiFirst_bid.getLocalValue() == null
                    || Double.parseDouble(uiFirst_bid.getLocalValue().toString()) == 0.0) {

                FacesMessage msg2 = new FacesMessage("First bid must be greater than zero");
                msg2.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(first_bidId, msg2);
                fc.renderResponse();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBuy_price() {
        return buy_price;
    }

    public double getFirst_bid() {
        return first_bid;
    }

    public String getLocation() {
        return location;
    }

    public String getEnds() {
        return ends;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<String> getCategories_() {
        return categories_;
    }

    public boolean isErr() {
        return err;
    }

    public Part getFile() {
        return file;
    }

    public List<Part> getFiles() {
        return files;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuy_price(double buy_price) {
        this.buy_price = buy_price;
    }

    public void setFirst_bid(double first_bid) {
        this.first_bid = first_bid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setCategories_(List<String> categories_) {
        this.categories_ = categories_;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void setFiles(List<Part> files) {
        this.files = files;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

}
