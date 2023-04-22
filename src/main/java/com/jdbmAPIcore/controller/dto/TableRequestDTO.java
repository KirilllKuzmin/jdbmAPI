package com.jdbmAPIcore.controller.dto;

import com.jdbmAPIcore.entity.Column;

import java.util.List;

public class TableRequestDTO {

    private String tableName;

    private List<Column> columns;

    public TableRequestDTO(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public TableRequestDTO() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
