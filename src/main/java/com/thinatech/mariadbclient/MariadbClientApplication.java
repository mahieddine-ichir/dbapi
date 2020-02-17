package com.thinatech.mariadbclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
@Slf4j
public class MariadbClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MariadbClientApplication.class, args);
	}
}
