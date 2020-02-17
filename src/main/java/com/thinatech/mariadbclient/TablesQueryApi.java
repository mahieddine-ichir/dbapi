package com.thinatech.mariadbclient;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("{table}")
    public Map<String, Object> listTable(@PathVariable("table") String table,
                                                     @RequestParam("order_by") String orderByProperty,
                                                     @RequestParam(value = "start", defaultValue = "${query.defaults.start:0}") Integer start,
                                                     @RequestParam(value = "limit", defaultValue = "${query.defaults.limit:100}") Integer limit,
                                                     GenericRepository.Sort sort) {
        Collection<Map<String, Object>> collection = genericRepository.select(table, orderByProperty, sort, start, limit);
        Map<String, Object> result = new HashMap<>();
        result.put("data", collection);
        getNext(start, limit, sort).ifPresent(o -> result.put("next", o));
        getPrevious(start, limit, sort).ifPresent(o -> result.put("previous", o));
        return result;
    }

    private Optional<Object> getPrevious(Integer start, Integer limit, GenericRepository.Sort sort) {
        return Optional.empty();
    }

    private Optional<Object> getNext(Integer start, Integer limit, GenericRepository.Sort sort) {
        if (sort == GenericRepository.Sort.DESC && start - limit < 0) {
            return Optional.empty();
        }
        return Optional.of(ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam("start", sort == GenericRepository.Sort.ASC ? start+limit : start-limit )
                .replaceQueryParam("limit", limit)
                .replaceQueryParam("sort", sort)
                .toUriString());
    }
}
