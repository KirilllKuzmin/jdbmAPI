package com.jdbmAPIcore.controller;

import com.jdbmAPIcore.controller.dto.InsertTableRequestDTO;
import com.jdbmAPIcore.controller.dto.TableRequestDTO;

import com.jdbmAPIcore.entity.Query.QueryResult;
import com.jdbmAPIcore.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

@RestController
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/createTable")
    public String createTable(@RequestBody TableRequestDTO tableRequestDTO, HttpServletRequest request) {
        return tableService.createTable(tableRequestDTO.getTableName(), tableRequestDTO.getColumns(), request);
    }

    @DeleteMapping("/dropTable/{tableName}")
    public String dropTable(@PathVariable String tableName, HttpServletRequest request) {
        return tableService.dropTable(tableName, request);
    }

    @PostMapping("/insertInto/{tableName}")
    public String insertInto(@PathVariable String tableName, @RequestBody InsertTableRequestDTO insertTableRequestDTO, HttpServletRequest request) {
        return tableService.insertInto(tableName, insertTableRequestDTO.getValues(), request);
    }

    @GetMapping("/tableColumns/{tableName}")
    public String getTableColumns(@PathVariable String tableName) {
        return tableService.getColumns(tableName);
    }

    @GetMapping("/selectFrom/{tableName}")
    public ResponseEntity<?> selectFrom(@PathVariable String tableName, HttpServletRequest request) {
        return tableService.selectFrom(tableName, request);
    }

    @PostMapping("/selectFrom/{tableName}/where")
    public ResponseEntity<?> selectFromWhere(@PathVariable String tableName, @RequestBody Map<String, String> filters, HttpServletRequest request) {
        return tableService.selectFromWhere(tableName, filters, request);
    }

    @DeleteMapping("/deleteFrom/{tableName}/{id}")
    public String deleteValues(@PathVariable String tableName, @PathVariable Long id, HttpServletRequest request) {
        return tableService.deleteFrom(tableName, id, request);
    }

    @PostMapping("/executeQuery")
    public QueryResult executeQuery(@RequestBody Map<String, String> req, HttpServletRequest request) throws SQLException {
        return tableService.executeQuery(req.get("sql"), request);
    }
}
