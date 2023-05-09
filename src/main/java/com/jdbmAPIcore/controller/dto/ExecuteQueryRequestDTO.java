package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность ответа вставки данных в таблицу")
public class ExecuteQueryRequestDTO {

    @Schema(description = "Пользовательский SQL запрос", example = "select * from my_table1;")
    private String sql;

    public ExecuteQueryRequestDTO(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
