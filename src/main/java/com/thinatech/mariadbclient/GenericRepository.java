package com.thinatech.mariadbclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository
public class GenericRepository {

    @Value("${query.select}")
    private String selectQuery;

    @Value("${query.findOne}")
    private String findOneQuery;

    private final JdbcTemplate jdbcTemplate;

    public GenericRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Map<String, Object>> select(String table, String orderByProperty, Sort sort, Integer start, Integer limit) {
        return jdbcTemplate.queryForList(String.format(selectQuery, table, orderByProperty, sort, start, limit));
    }

    public Collection<Map<String, Object>> find(String table, String criteria) {
        return jdbcTemplate.queryForList(String.format(findOneQuery, table, criteria));
    }

    enum Sort {
        ASC,
        DESC;
    }
}
