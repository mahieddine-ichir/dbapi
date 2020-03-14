package com.thinatech.dbapi;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GenericRowMapperToMap implements RowMapper<Map<String, String>> {
    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, String> hashMap = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i=1; i<=metaData.getColumnCount(); i++) {
            String columnLabel = metaData.getColumnLabel(i);
            hashMap.put(columnLabel, rs.getString(columnLabel));
        }

        return hashMap;
    }
}
