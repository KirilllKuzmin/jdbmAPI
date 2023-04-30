package com.jdbmAPIcore.entity.Query;

import java.util.List;

public class QueryResult {

    private Integer columnCount;

    private Integer rowCount;

    private List<QueryRow> rows;

    public QueryResult(Integer columnCount, Integer rowCount, List<QueryRow> rows) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.rows = rows;
    }

    public QueryResult(String e) {
    }

    public QueryResult() {
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public QueryResult(Integer columnCount, List<QueryRow> rows) {
        this.columnCount = columnCount;
        this.rows = rows;
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public List<QueryRow> getRows() {
        return rows;
    }

    public void setRows(List<QueryRow> rows) {
        this.rows = rows;
    }
}
