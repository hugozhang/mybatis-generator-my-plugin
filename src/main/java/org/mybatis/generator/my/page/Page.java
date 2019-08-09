package org.mybatis.generator.my.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Page<T> implements Serializable {

    private int pageNo = 1;

    private int pageSize = 15;

    private int total = 0;

    private int totalPage = 0;

    private Collection<T> results = new ArrayList<T>();

    public Page() {

    }

    public Page(int pageNo, int pageSize, int total, List<T> results) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.results = results;
    }

    public int getTotalPage() {
        if (total % pageSize == 0) {
            totalPage = total/pageSize;
        } else {
            totalPage = total/pageSize + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

}
