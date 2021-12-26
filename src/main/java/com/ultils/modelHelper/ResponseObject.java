package com.ultils.modelHelper;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private String status;
    private String message;
    private Object data;
    private long pageIndex;
    private long pageSize;
    private long count;

    public ResponseObject() {
    }

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(String status, String message, Object data, long count) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public ResponseObject(String status, String message, Object data, long pageIndex, long pageSize, long count) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
