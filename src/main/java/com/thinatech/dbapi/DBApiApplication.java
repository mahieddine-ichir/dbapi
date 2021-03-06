package com.thinatech.dbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class DBApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBApiApplication.class, args);
	}

}
