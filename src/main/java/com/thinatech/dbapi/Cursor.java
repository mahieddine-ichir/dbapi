package com.thinatech.dbapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cursor {

    private Integer start = 0;

    private Integer limit = 50;

    public Cursor nextPage(Sort sort) {
        switch (sort) {
            case DESC:
                return new Cursor(start-limit, limit);
            case ASC:
            default:
                return new Cursor(start+limit, limit);
        }
    }

    public Cursor previousPage(Sort sort) {
        switch (sort) {
            case DESC:
                return new Cursor(start+limit, limit);
            case ASC:
            default:
                return new Cursor(Math.max(start - limit, 0), limit);
        }
    }
}
