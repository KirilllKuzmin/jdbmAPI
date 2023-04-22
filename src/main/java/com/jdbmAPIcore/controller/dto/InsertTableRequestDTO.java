package com.jdbmAPIcore.controller.dto;

import java.util.List;
import java.util.Map;

public class InsertTableRequestDTO {

    private String tableName;

    private List<Map<String, Object>> values;

    public InsertTableRequestDTO(String tableName, List<Map<String, Object>> values) {
        this.tableName = tableName;
        this.values = values;
    }

    public InsertTableRequestDTO() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Map<String, Object>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, Object>> values) {
        this.values = values;
    }
}
