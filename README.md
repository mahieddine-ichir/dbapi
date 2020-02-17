# Description
Primarily developed to expose a MariaDB / Mysql database tables through a single and simple API (only GET),
using Spring Boot starters (aiming to contain very few lines of code).

## Adapting to another DB Engine
Can be easily reworked to expose other Database engines by changing:

* the maven dependency

```
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
```
to the required one (MSQL Server, Postgres, ... etc)

* the engine connection properties

```
spring.datasource:
  url: jdbc:mysql://localhost:6603/wdm-b2b
  username: root
  password: password
  driver-class-name: com.mysql.cj.jdbc.Driver
``` 
to appropriate ones (depending on the chosen engine)

* Changing the property
```
query:
  select: SELECT * FROM %s ORDER BY %s %s limit %d, %d
```
according to the chosen DB engine (the above is an example for a Maria / Mysql engine).

The different replacements (`%s` and `%d`) stand for table name, ordering column, ordering direction (ASC, DESC), started cursor position
and results limit.
  

## API access credentials
Support only Basic Authentication (spring security starter). Login and password can be changed by overriding

```
spring.security.user:
  name: thinatech
  password: password
```

## Exposed API
The application exposes a single API to list a table
```
    http://localhost:8080/tables/{table}?order_by={table_id}
```
change `{table}` and `{table_id}` accordingly.

Pagination is supported though additional query parameters:
```
    http://localhost:8080/tables/{table}?order_by={table_id}&start=0&limit=50&sort=desc
```

* `sort` can be either `asc` or `desc`
* `start` and `limit` are positive integers (for cursor based navigation)

The default parameters of these can be overridden through:
```
query:
  defaults:
    sort: DESC
    limit: 100
    start: 0
``` 
