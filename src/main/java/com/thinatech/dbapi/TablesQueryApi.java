package com.thinatech.dbapi;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("tables")
public class TablesQueryApi {

    private final GenericRepository genericRepository;

    public TablesQueryApi(GenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    @GetMapping
    public Collection<String> tables() throws SQLException {
        return genericRepository.tables();
    }

    @GetMapping("{table}/_search")
    public Collection<Map<String, String>> search(@PathVariable("table") String table,
                                                  @RequestParam(value="q", required = false) String query,
                                                  Cursor cursor
                                                  ) {
        String criteria = "";
        if (! StringUtils.isEmpty(query)) {
            criteria = query.replaceAll(":", "=")
                    .replaceAll(",", " AND ");
        }
        return genericRepository.search(table, criteria, cursor);
    }

    @GetMapping("{table}")
    public Map<String, Object> select(@PathVariable("table") String table,
                                      @RequestParam("order_by") String orderByProperty,
                                      Cursor cursor, Sort sort) {
        Collection<Map<String, String>> data = genericRepository.select(table, orderByProperty, sort, cursor);

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        getNext(cursor, sort).ifPresent(o -> result.put("next", o));
        getPrevious(cursor, sort).ifPresent(o -> result.put("previous", o));
        return result;
    }

    @GetMapping("{table}/_schema")
    public Map<String, String> schema(@PathVariable String table) throws SQLException {
        return genericRepository.schema(table);
    }

    @PostMapping(value="{table}/_schema", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> updateSchema(@PathVariable String table, @RequestBody QueryForUpdate queryDefinition) throws SQLException {
        return genericRepository.updateSchema(table, queryDefinition.getQuery());
    }

    private Optional<Object> getPrevious(Cursor cursor, Sort sort) {
        Cursor previousPage = cursor.previousPage(sort);
        return Optional.of(ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam("start", previousPage.getStart())
                .replaceQueryParam("limit", previousPage.getLimit())
                .replaceQueryParam("sort", sort)
                .toUriString());
    }

    private Optional<Object> getNext(Cursor cursor, Sort sort) {
        Cursor nextPage = cursor.nextPage(sort);
        return Optional.of(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("start", nextPage.getStart())
                    .replaceQueryParam("limit", nextPage.getLimit())
                    .replaceQueryParam("sort", sort)
                    .toUriString());
    }
}
