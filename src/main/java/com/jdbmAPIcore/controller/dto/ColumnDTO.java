package com.jdbmAPIcore.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность атрибута таблицы")
public class ColumnDTO {

    @Schema(description = "Название атрибута", example = "Название")
    private String name;

    @Schema(description = "Название атрибута", example = "Тип")
    private String type;

    public ColumnDTO(){}

    public ColumnDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
