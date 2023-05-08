package com.jdbmAPIcore.controller.dto.QueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Сущность строки SQL запроса")
public class QueryRow {

    @Schema(description = "Строка")
    private List<QueryValue> row;

    public List<QueryValue> getRow() {
        return row;
    }

    public void setRow(List<QueryValue> row) {
        this.row = row;
    }
}
