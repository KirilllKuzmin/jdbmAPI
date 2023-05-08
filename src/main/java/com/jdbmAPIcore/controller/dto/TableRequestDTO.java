package com.jdbmAPIcore.controller.dto;

import java.util.List;

public class TableRequestDTO {

    private String tableName;

    private List<ColumnDTO> columnDTOS;

    public TableRequestDTO(String tableName, List<ColumnDTO> columnDTOS) {
        this.tableName = tableName;
        this.columnDTOS = columnDTOS;
    }

    public TableRequestDTO() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnDTO> getColumnDTOS() {
        return columnDTOS;
    }

    public void setColumnDTOS(List<ColumnDTO> columnDTOS) {
        this.columnDTOS = columnDTOS;
    }
}
