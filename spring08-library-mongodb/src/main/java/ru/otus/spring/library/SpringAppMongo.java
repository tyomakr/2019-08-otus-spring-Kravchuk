package ru.otus.spring.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class SpringAppMongo {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppMongo.class);
    }
}
