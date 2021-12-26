package com.ultils.specification;

public class SearchCriteria {
    private String column;
    private String keySearch;
    private String expression;

    public SearchCriteria() {
    }

    public SearchCriteria(String column, String keySearch, String expression) {
        this.column = column;
        this.keySearch = keySearch;
        this.expression = expression;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
