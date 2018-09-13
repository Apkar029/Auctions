/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.CategoryDao;
import dao.ItemDao;
import entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "browseBean")
@ViewScoped
public class BrowseBean {

    List<String> categories;
    List<String> fiveCategories = new ArrayList();
    List<String> restCategories = new ArrayList();
    private List<Integer> dummyList = new ArrayList<Integer>();
    private List<Item> items = new ArrayList<Item>();
    private int pageNum = 0;
    private int itemsCount = 0;
    private final int pageSize = 6;
    private int lastPage = 0;
    String category;

    /**
     * Creates a new instance of browseBean
     */
    public BrowseBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("BrowseBean: init");
        CategoryDao categoryDao = new CategoryDao();
        categories = categoryDao.getAllCategories();
        int count = 0;
        for (String c : categories) {
            if (count < 5) {
                fiveCategories.add(c);
            } else {
                restCategories.add(c);
            }
            count++;
        }
        
        for (String c : fiveCategories) {
            System.out.println("fiveCatefories: " + c);
        }
        for (String c : restCategories) {
            System.out.println("restCatefories: " + c);
        }

    }

    public void reset() {
        if (category != null) {
            System.out.println("BrowserBean: init: category: " + category);
        }
        pageNum = 0;
        itemsCount = this.count();
        lastPage = (itemsCount + pageSize - 1) / pageSize;
        dummyList.clear();
        if (itemsCount > 0) {
            for (int n = 1; n <= 6 && n < lastPage; n++) {
                dummyList.add(n);
            }
            dummyList.add(lastPage);
            for (Integer c : dummyList) {
                System.out.println("BrowseBean: reset: c = " + c);
            }
        } else {
            System.out.println("BrowseBean: reset: itemsCount=0");
        }
    }

    public void submitCategory() {
        System.out.println("BrowseBean: submitCategory: Category: " + category);
        getActiveItems(pageNum);
        if (dummyList.isEmpty()) {
            this.reset();
        }
    }

    public void move(int to) {
        System.out.println("BrowseBean: move: to: " + to);
        pageNum = to - 1;
        dummyList.clear();
        dummyList.add(1);
        System.out.println("Move to: " + to);
        System.out.println("BrowseBean: move: pageNum: " + pageNum);
        System.out.println("BrowseBean: move: lastPage - pageNum: " + (lastPage - pageNum));
        if (lastPage == 5) {
            for (int n = 2; n < 5; n++) {
                dummyList.add(n);
            }
        } else if (pageNum + 1 >= 4 && lastPage - pageNum >= 4) {
            System.out.println(1);
            for (int n = to - 2; n <= to + 2; n++) {
                dummyList.add(n);
            }
        } else if (pageNum + 1 <= 4) {
            System.out.println(2);
            for (int n = 2; n <= 5 && n < lastPage; n++) {
                dummyList.add(n);
            }
        } else if (lastPage - pageNum < 4) {
            System.out.println(3);
            for (int n = lastPage - 4; n < lastPage; n++) {
                dummyList.add(n);
            }
        }
        dummyList.add(lastPage);
    }

    public int count() {
        ItemDao itemDao = new ItemDao();
        return itemDao.categoryCount(category);
    }

    private void getActiveItems(int from) {
        System.out.println("BrowseBean: getActiveItems");
        if (items != null) {
            items.clear();
        }
        ItemDao itemDao = new ItemDao();
        items = itemDao.findCategoryRange(new int[]{(from * pageSize), (from * pageSize) + pageSize}, category);

    }

    public List<Integer> getDummyList() {
        return dummyList;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getFiveCategories() {
        return fiveCategories;
    }

    public List<String> getRestCategories() {
        return restCategories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setDummyList(List<Integer> dummyList) {
        this.dummyList = dummyList;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFiveCategories(List<String> fiveCategories) {
        this.fiveCategories = fiveCategories;
    }

    public void setRestCategories(List<String> restCategories) {
        this.restCategories = restCategories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
