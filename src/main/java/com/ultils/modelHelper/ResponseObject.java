package com.ultils.modelHelper;

import java.io.Serializable;

public class ResponseObject extends ResponseObjectBase implements Serializable {
    private long pageIndex;
    private long pageSize;
    private long count;

    public ResponseObject() {
    }

    public ResponseObject(String status, String message, Object data) {
        super(status, message, data);
    }

    public ResponseObject(String status, String message, Object data, long count) {
        super(status, message, data);
        this.count = count;
    }

    public ResponseObject(String status, String message, Object data, long pageIndex, long pageSize, long count) {
        super(status, message, data);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.count = count;
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
}
