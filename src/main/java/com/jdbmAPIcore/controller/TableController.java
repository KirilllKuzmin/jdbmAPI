package com.jdbmAPIcore.controller;

import com.jdbmAPIcore.controller.dto.InsertTableRequestDTO;
import com.jdbmAPIcore.controller.dto.TableRequestDTO;

import com.jdbmAPIcore.controller.dto.QueryDTO.QueryResult;
import com.jdbmAPIcore.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "JWT")
@Tag(name="Взаимодействие с БД", description="Данные методы позводяют пользователям управлять БД")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @Operation(
            summary = "Создание таблицы",
            description = "Позволяет создать таблицу в текущей схеме пользователя с любыми параметрами и данными"
    )
    @PostMapping("/createTable")
    public String createTable(@RequestBody TableRequestDTO tableRequestDTO, HttpServletRequest request) {
        return tableService.createTable(tableRequestDTO.getTableName(), tableRequestDTO.getColumnDTOS(), request);
    }

    @Operation(
            summary = "Удаление таблицы",
            description = "Позволяет удалять таблицы в текущей схеме пользователя. (Админ не может удалить системные таблицы, в том числе с пользователями)"
    )
    @DeleteMapping("/dropTable/{tableName}")
    public String dropTable(@PathVariable @Parameter(description = "Название таблицы") String tableName, HttpServletRequest request) {
        return tableService.dropTable(tableName, request);
    }

    @Operation(
            summary = "Вставка в таблицу",
            description = "Позволяет вставлять данные в любую таблицу текущей схемы ползьзователя в любом объеме"
    )
    @PostMapping("/insertInto/{tableName}")
    public String insertInto(@PathVariable @Parameter(description = "Название таблицы") String tableName, @RequestBody InsertTableRequestDTO insertTableRequestDTO, HttpServletRequest request) {
        return tableService.insertInto(tableName, insertTableRequestDTO.getValues(), request);
    }

    @Operation(
            summary = "Получение атрбутов таблицы",
            description = "Позволяет получить все данные о структуре указанной таблицы"
    )
    @GetMapping("/tableColumns/{tableName}")
    public List<Map<String, Object>> getTableColumns(@PathVariable @Parameter(description = "Название таблицы") String tableName) {
        return tableService.getColumns(tableName);
    }

    @Operation(
            summary = "Получение всех данных из таблицы",
            description = "Позволяет извлечь все данные из указанной таблицы"
    )
    @GetMapping("/selectFrom/{tableName}")
    public ResponseEntity<?> selectFrom(@PathVariable @Parameter(description = "Название таблицы") String tableName, HttpServletRequest request) {
        return tableService.selectFrom(tableName, request);
    }

    @PostMapping("/selectFrom/{tableName}/where")
    public ResponseEntity<?> selectFromWhere(@PathVariable @Parameter(description = "Название таблицы") String tableName,
                                             @RequestBody @Parameter(description = "Фильтр или указание ограничений выборки во фразе WHERE") Map<String, String> filters, HttpServletRequest request) {
        return tableService.selectFromWhere(tableName, filters, request);
    }

    @Operation(
            summary = "Удаление данных",
            description = "Позволяет удалять данные из из таблицы в схеме пользователя"
    )
    @DeleteMapping("/deleteFrom/{tableName}/{id}")
    public String deleteValues(@PathVariable @Parameter(description = "Название таблицы") String tableName, @PathVariable Long id, HttpServletRequest request) {
        return tableService.deleteFrom(tableName, id, request);
    }

    @Operation(
            summary = "SQL запрос",
            description = "Позволяет извлечь любой SQL запрос (данные извлекаются исключительно из доступной схемы)"
    )
    @PostMapping("/executeQuery")
    public QueryResult executeQuery(@RequestBody @Parameter(description = "SQL запрос") Map<String, String> sqlRequest, HttpServletRequest request) throws SQLException {
        return tableService.executeQuery(sqlRequest.get("sql"), request);
    }
}
