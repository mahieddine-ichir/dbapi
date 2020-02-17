package com.thinatech.mariadbclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GenericRepository {

    @Value("${query.select}")
    private String selectQuery;

    private final JdbcTemplate jdbcTemplate;

    public GenericRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Map<String, Object>> select(String table, String orderByProperty, Sort sort, Integer start, Integer limit) {
        return jdbcTemplate.query(String.format(selectQuery, table, orderByProperty, sort, start, limit),
                (rs, rowNum) -> {

                    HashMap hashMap = new HashMap();
                    ResultSetMetaData metaData = rs.getMetaData();
                    for (int i=1; i<=metaData.getColumnCount(); i++) {
                        String columnLabel = metaData.getColumnLabel(i);
                        hashMap.put(columnLabel, rs.getString(columnLabel));
                    }

                    return hashMap;
                });
    }

    enum Sort {
        ASC,
        DESC;
    }
}
