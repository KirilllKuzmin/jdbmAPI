package com.jdbmAPIcore.controller;

import com.jdbmAPIcore.controller.dto.InsertTableRequestDTO;
import com.jdbmAPIcore.controller.dto.TableRequestDTO;

import com.jdbmAPIcore.entity.Column;
import com.jdbmAPIcore.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/createTable")
    public String createTable(@RequestBody TableRequestDTO tableRequestDTO) {
        return tableService.createTable(tableRequestDTO.getTableName(), tableRequestDTO.getColumns());
    }

    @DeleteMapping("/dropTable/{tableName}")
    public String dropTable(@PathVariable String tableName) {
        return tableService.dropTable(tableName);
    }

    @PostMapping("/insertInto/{tableName}")
    public String insertInto(@PathVariable String tableName, @RequestBody InsertTableRequestDTO insertTableRequestDTO) {
        return tableService.insertInto(tableName, insertTableRequestDTO.getValues());
    }

    @GetMapping("/tableColumns/{tableName}")
    public String getTableColumns(@PathVariable String tableName) {
        return tableService.getColumns(tableName);
    }

    @DeleteMapping("/deleteFrom/{tableName}/{values}")
    public String deleteValues(@PathVariable String tableName, @PathVariable Long id) {
        return tableService.deleteFrom(tableName, id);
    }
}
