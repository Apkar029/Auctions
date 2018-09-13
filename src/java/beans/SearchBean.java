/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.ItemDao;
import entities.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "searchBean")
@ViewScoped
public class SearchBean implements Serializable {

    /**
     * Creates a new instance of SearchBean
     */
    private String query;

    private List<Item> items = new ArrayList<Item>();

    private List<Integer> dummyList = new ArrayList<Integer>();

    private int firstItemIndex = 0;
    private int pageNum = 0;
    private int itemsCount = 0;
    private final int pageSize = 6;
    private int lastPage = 0;

    private void getActiveItems(int from) {
        System.out.println("SearchBean: getActiveItems");
        if (items != null) {
            items.clear();
        }
        ItemDao itemDao = new ItemDao();
        items = itemDao.findRange(new int[]{(from * pageSize), (from * pageSize) + pageSize}, query);

    }

    public SearchBean() {
    }

    public int count() {
        ItemDao itemDao = new ItemDao();
        return itemDao.count(query);
    }

    public void reset() {
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
                System.out.println("SearchBean: reset: c = " + c);
            }
        } else {
            System.out.println("SearchBean: reset: itemsCount=0");
        }
    }

    public void submitQuery() {
        System.out.println("Query: " + query);
        getActiveItems(pageNum);
        if (dummyList.isEmpty()) {
            this.reset();
        }
    }

    public void move(int to) {
        System.out.println("SearchBean: move: to: " + to);
        pageNum = to - 1;
        dummyList.clear();
        dummyList.add(1);
        System.out.println("Move to: " + to);
        System.out.println("SearchBean: move: pageNum: " + pageNum);
        System.out.println("SearchBean: move: lastPage - pageNum: " + (lastPage - pageNum));
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

    public List<Item> getItems() {
        if (this.items == null) {
            ItemDao itemDao = new ItemDao();
            this.items = itemDao.getAllItems(query);
        }
        return items;
    }

    public String getQuery() {
        return query;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void next() {
        pageNum++;
        getActiveItems(pageNum);
    }

    public void previous() {
        pageNum--;
        getActiveItems(pageNum);
    }

    public List getDummyList() {
        return dummyList;
    }

    public int getFirstItemIndex() {
        return firstItemIndex;
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

    public void setDummyList(List dummyList) {
        this.dummyList = dummyList;
    }

    public void setFirstItemIndex(int firstItemIndex) {
        this.firstItemIndex = firstItemIndex;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

}
