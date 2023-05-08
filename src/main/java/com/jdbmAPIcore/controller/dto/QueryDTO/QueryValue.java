package com.jdbmAPIcore.controller.dto.QueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность значения SQL запроса")
public class QueryValue {

    @Schema(description = "Название атрибута", example = "Name")
    private String columnName;

    @Schema(description = "Тип атрибута", example = "data type")
    private String columnType;

    @Schema(description = "Значение", example = "Значение")
    private String value;

    public QueryValue(String columnName, String columnType, String value) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QueryValue{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
