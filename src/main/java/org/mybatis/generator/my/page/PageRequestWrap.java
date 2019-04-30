package org.mybatis.generator.my.page;

import java.io.Serializable;

public class PageRequestWrap<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2792346671122455743L;

    private Integer pageNo = 1;

    private Integer pageSize = 15;

    private String orderBy;

    private String orderSort;

    private T filters;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public T getFilters() {
        return filters;
    }

    public void setFilters(T filters) {
        this.filters = filters;
    }

    public Integer getStartOffset() {
        return (pageNo - 1) * pageSize;
    }

}
