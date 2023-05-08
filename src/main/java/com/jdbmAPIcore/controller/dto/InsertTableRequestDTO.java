package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Сущность ответа вставки данных в таблицу")
public class InsertTableRequestDTO {

    @Schema(description = "Название таблицы", example = "my_table1")
    private String tableName;

    @Schema(description = "Значение вставляемых данных")
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
