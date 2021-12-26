package com.ultils.modelHelper;

import java.util.List;

public class ModelResult<T> {
    private List<T> listResult;
    private long count;

    public ModelResult() {
    }

    public ModelResult(List<T> listResult, long count) {
        this.listResult = listResult;
        this.count = count;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}