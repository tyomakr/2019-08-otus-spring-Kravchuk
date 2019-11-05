package ru.otus.spring.library.webmvc.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringBootMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.spring.library.webmvc.config.changelog";
    private static final String DB_NAME = "libmongo";
    private final ApplicationContext springContext;
    private final MongoClient mongoClient;

    public AppConfig(ApplicationContext springContext, MongoClient mongoClient) {
        this.springContext = springContext;
        this.mongoClient = mongoClient;
    }

    @PostConstruct
    public void init () {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mongock mongock = new SpringBootMongockBuilder(mongoClient, DB_NAME, CHANGELOGS_PACKAGE)
                .setApplicationContext(springContext)
                .setLockQuickConfig()
                .build();
        mongock.execute();
    }
}

