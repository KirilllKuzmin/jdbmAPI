package com.jdbmAPIcore.controller.dto.QueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Результат SQL запроса")
public class QueryResult {

    @Schema(description = "Количество атрибутов", example = "0")
    private Integer columnCount;

    @Schema(description = "Количество строк", example = "0")
    private Integer rowCount;

    @Schema(description = "Строки")
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
