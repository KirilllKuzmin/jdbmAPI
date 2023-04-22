package com.jdbmAPIcore.service;

import com.google.gson.Gson;
import com.jdbmAPIcore.entity.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TableService() {
    }

    public String createTable(String tableName, List<Column> columns) {

        StringBuilder createTableQuery = new StringBuilder();
        createTableQuery.append("create table ")
                .append(tableName)
                .append(" (");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            createTableQuery.append(column.getName())
                    .append(" ")
                    .append(column.getType());

            if (i != columns.size() - 1)
                createTableQuery.append(", ");
        }

        createTableQuery.append(")");

        jdbcTemplate.execute(createTableQuery.toString());

        return "Table " + tableName + " has been created successfully";
    }

    public String dropTable(String tableName) {

        String sql = "DROP TABLE ?";

        jdbcTemplate.update(sql, tableName);

        return "The " + tableName + " table has been dropped";
    }

    public String insertInto(String tableName, List<Map<String, Object>> values) {

        StringBuilder insertQuery = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            insertQuery.append("insert into ")
                    .append(tableName)
                    .append(" values (");

            List<String> columnNames = new ArrayList<>();
            values.get(0).forEach((columnName, value) -> columnNames.add(columnName));

            Map<String, Object> row = values.get(i);

            for (int j = 0; j < columnNames.size(); j++) {
                Object value = row.get(columnNames.get(j));

                if (value instanceof String)
                    insertQuery.append("'").append(value).append("'");
                else
                    insertQuery.append(value);

                if (j != columnNames.size() - 1)
                    insertQuery.append(", ");
            }

            insertQuery.append(");");
        }

        jdbcTemplate.execute(insertQuery.toString());

        return "Data has been inserted successfully into " + tableName;
    }

    public String getColumns(String tableName) {

        String sql = "select column_name, column_type from information_schema.columns where table_name = upper(?)";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, tableName);

        String jsonResult = new Gson().toJson(result);

        return "{\"COLUMNS\":" + jsonResult + "}";
    }

    public String deleteFrom(String tableName, Long id) {

        String sql = "delete from " + tableName + " where id = ?";

        jdbcTemplate.update(sql, id);

        return "Delete values " + id + " from " + tableName;
    }
}
