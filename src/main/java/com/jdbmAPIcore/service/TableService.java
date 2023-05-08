package com.jdbmAPIcore.service;

import com.jdbmAPIcore.controller.dto.ColumnDTO;
import com.jdbmAPIcore.controller.dto.QueryDTO.QueryResult;
import com.jdbmAPIcore.controller.dto.QueryDTO.QueryRow;
import com.jdbmAPIcore.controller.dto.QueryDTO.Mapper.QueryRowMapper;
import com.jdbmAPIcore.controller.dto.QueryDTO.QueryValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

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

    private static final Logger log = Logger.getLogger(TableService.class);

    public String createTable(String tableName, List<ColumnDTO> columnDTOS, HttpServletRequest request) {
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

        for (int i = 0; i < columnDTOS.size(); i++) {
            ColumnDTO columnDTO = columnDTOS.get(i);
            createTableQuery.append(columnDTO.getName())
                    .append(" ")
                    .append(columnDTO.getType());

            if (i != columnDTOS.size() - 1)
                createTableQuery.append(", ");
        }

        createTableQuery.append(")");
        log.debug(createTableQuery.toString());

        jdbcTemplate.execute(createTableQuery.toString());

        return "Table " + tableName + " has been created successfully";
    }

    public String dropTable(String tableName, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "DROP TABLE " + username.get("sub", String.class) + "_schema." + tableName;
        log.debug(sql);

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
        log.debug(insertQuery.toString());

        jdbcTemplate.execute(insertQuery.toString());

        return "Data has been inserted successfully into " + tableName;
    }

    public List<Map<String, Object>> getColumns(String tableName) {
        String sql = "select column_name, data_type from information_schema.columns where table_name = ?";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, tableName);
        log.debug(sql);

       // String jsonResult = new Gson().toJson(result);

        //return "{\"COLUMNS\":" + jsonResult + "}";
        return result;
    }

    public String deleteFrom(String tableName, Long id, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "delete from " + username.get("sub", String.class) + "_schema." + tableName + " where id = " + id;
        log.debug(sql);

        jdbcTemplate.execute(sql);

        return "Delete values " + id + " from " + tableName;
    }

    public ResponseEntity<?> selectFrom(String tableName, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        String sql = "select * from " + username.get("sub", String.class) + "_schema." + tableName;
        log.debug(sql);

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
        log.debug(sql);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return new ResponseEntity<>(rows, HttpStatus.OK);
    }

    public QueryResult executeQuery(String sql, HttpServletRequest request) throws SQLException {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            token = bearerToken.substring(7, bearerToken.length());

        Claims username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        QueryResult result = new QueryResult();

        String setSchema = "SET search_path TO " + username.get("sub", String.class) + "_schema" + ",public;";
        log.debug(setSchema);

        jdbcTemplate.execute(setSchema);

        try {
            List<List<QueryValue>> rows = jdbcTemplate.query(sql, new QueryRowMapper());

            log.debug(sql);
            log.debug(rows);

            List<QueryRow> resultRows = new ArrayList<>();
            List<QueryValue> rowValues = new ArrayList<>();

            for (int i = 0; i < rows.size(); i++) {
                rowValues = rows.get(i);
                QueryRow resultRow = new QueryRow();
                resultRow.setRow(rowValues);
                resultRows.add(resultRow);
            }

            result = new QueryResult(rowValues.size(), resultRows.size(), resultRows);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return result;
    }
}
