package com.jdbmAPIcore.service;

import com.google.gson.Gson;
import com.jdbmAPIcore.config.component.TokenFilter;
import com.jdbmAPIcore.entity.Column;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String secret;
    private String token;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public TableService() {
    }

    public String createTable(String tableName, List<Column> columns, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        Boolean isAdmin = username.get("isAdmin", Boolean.class);

        StringBuilder createTableQuery = new StringBuilder();

        /*createTableQuery.append("create table ")
                .append(username.get("sub", String.class));

        if (isAdmin != null && isAdmin) {
            createTableQuery.append("public.");
        } else {
            createTableQuery.append(username.get("sub", String.class))
                    .append("_schema.");
        }

        createTableQuery.append(tableName)
                .append(" (");*/

        createTableQuery.append("create table ")
                .append(username.get("sub", String.class))
                .append("_schema.")
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

    public String dropTable(String tableName, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "DROP TABLE " + username.get("sub", String.class) + "_schema." + tableName;

        jdbcTemplate.execute(sql);

        return "The " + tableName + " table has been dropped";
    }

    public String insertInto(String tableName, List<Map<String, Object>> values, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        StringBuilder insertQuery = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            insertQuery.append("insert into ")
                    .append(username.get("sub", String.class))
                    .append("_schema.")
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
        String sql = "select column_name, data_type from information_schema.columns where table_name = ?";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, tableName);

        String jsonResult = new Gson().toJson(result);

        return "{\"COLUMNS\":" + jsonResult + "}";
    }

    public String deleteFrom(String tableName, Long id, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "delete from " + username.get("sub", String.class) + "_schema." + tableName + " where id = " + id;

        jdbcTemplate.execute(sql);

        return "Delete values " + id + " from " + tableName;
    }

    public ResponseEntity<?> selectFrom(String tableName, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "select * from " + username.get("sub", String.class) + "_schema." + tableName;

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<?> selectFromWhere(String tableName, Map<String, String> filters, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        StringBuilder sqlBuilder = new StringBuilder("select * from " + username.get("sub", String.class) + "_schema." + tableName);
        if (filters != null && filters.size() > 0) {
            sqlBuilder.append(" WHERE");
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                sqlBuilder.append(" ").append(entry.getKey()).append(" = " + entry.getValue() + " AND");
            }
            sqlBuilder.delete(sqlBuilder.length() - 4, sqlBuilder.length());
        }
        String sql = sqlBuilder.toString();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }
}
