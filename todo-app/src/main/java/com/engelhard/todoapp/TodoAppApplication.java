package com.engelhard.todoapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TodoAppApplication {
	
	private static final Logger log = LoggerFactory.getLogger(TodoAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

	@Bean
	public CommandLineRunner commandLineRunner(DataSource dataSource) {
		return args -> {
			try {
				dataSource.getConnection();
				log.info("Database connection established");
			} catch (Exception e) {
				log.error("Databse connection failed: ", e);
			}
		};
	}
}
