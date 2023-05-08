package com.jdbmAPIcore.controller.dto.QueryDTO.Mapper;

import com.jdbmAPIcore.controller.dto.QueryDTO.QueryValue;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryRowMapper implements RowMapper<List<QueryValue>> {

    @Override
    @Nullable
    public List<QueryValue> mapRow(ResultSet resultSet, int i) throws SQLException {

        List<QueryValue> rowValues = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int j = 1; j < columnCount + 1; j++) {
            String columnName = metaData.getColumnLabel(j);
            String columnType = metaData.getColumnTypeName(j);
            String columnValue = resultSet.getString(j);
            QueryValue value = new QueryValue(columnName, columnType, columnValue);
            rowValues.add(value);
        }

        return rowValues;
    }
}
