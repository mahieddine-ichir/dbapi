package com.thinatech.dbapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

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

    public Collection<Map<String, String>> select(String table, String orderByProperty, Sort sort, Cursor cursor) {
        return jdbcTemplate.query(String.format(selectQuery, table, orderByProperty, sort, cursor.getStart(), cursor.getLimit()),
                new GenericRowMapperToMap());
    }

    public Collection<Map<String, String>> search(String table, String criteria, Cursor cursor) {
        String query = String.format(findOneQuery, table, criteria, cursor.getStart(), cursor.getLimit());
        if (StringUtils.isEmpty(criteria)) {
            query = query.replaceAll(" WHERE\\s+%s ", " ");
        }
        return jdbcTemplate.query(query, new GenericRowMapperToMap());
    }

    public Map<String, String> schema(String table) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        return getTableColumns(table, connection, metaData);
    }

    public Map<String, String> updateSchema(String table, String queryString) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement query = connection.prepareStatement(queryString);
        query.execute();
        return schema(table);
    }

    public Collection<String> tables() throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getTables(null, null, "%", null);
        List<String> tables = new ArrayList<>(resultSet.getFetchSize());
        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }
        return tables;
    }

    private Map<String, String> getTableColumns(String table, Connection connection, DatabaseMetaData metaData) throws SQLException {
        ResultSet columns = metaData.getColumns(null, null, table, null);
        Map<String, String> columnsToTypes = new HashMap<>(columns.getFetchSize());
        while (columns.next()) {
            columnsToTypes.put(columns.getString("COLUMN_NAME"), columns.getString("DATA_TYPE"));
        }
        return columnsToTypes;
    }
}
