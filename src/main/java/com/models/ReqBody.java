package com.models;

public class ReqBody {
    private int pageIndex;
    private int pageSize;
    private String sortColumn;
    private String sortOrder;

    public ReqBody() {}

    public ReqBody(int pageIndex, int pageSize, String sortColumn, String sortOrder) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sortColumn = sortColumn;
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "ReqBody{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortColumn='" + sortColumn + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
