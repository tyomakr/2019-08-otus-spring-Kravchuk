package ru.otus.spring.migration.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.migration.domain.mongodb.AuthorMongo;
import ru.otus.spring.migration.domain.mongodb.BookMongo;
import ru.otus.spring.migration.domain.mongodb.CommentMongo;
import ru.otus.spring.migration.domain.mongodb.GenreMongo;
import ru.otus.spring.migration.domain.sql.AuthorSql;
import ru.otus.spring.migration.domain.sql.BookSql;
import ru.otus.spring.migration.domain.sql.CommentSql;
import ru.otus.spring.migration.domain.sql.GenreSql;
import ru.otus.spring.migration.repository.sql.AuthorSqlRepository;
import ru.otus.spring.migration.repository.sql.BookSqlRepository;
import ru.otus.spring.migration.repository.sql.CommentSqlRepository;
import ru.otus.spring.migration.repository.sql.GenreSqlRepository;
import ru.otus.spring.migration.service.ConvertService;

import java.util.Collections;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    private final AuthorSqlRepository authorSqlRepository;
    private final GenreSqlRepository genreSqlRepository;
    private final BookSqlRepository bookSqlRepository;
    private final CommentSqlRepository commentSqlRepository;

    private final ConvertService convertService;
    private final MongoTemplate mt;


    @Bean
    public Job jobMigration(Step stepAuthors, Step stepGenres, Step stepBooks, Step stepComments) {
        return jobBuilderFactory.get("jobMigration")
                .incrementer(new RunIdIncrementer())
                .start(stepAuthors)
                .next(stepGenres)
                .next(stepBooks)
                .next(stepComments)
                .build();
    }



    //STEPS Migration
    @Bean
    public Step stepAuthors(RepositoryItemReader<AuthorSql> authorItemReader,
                            ItemProcessor<AuthorSql, AuthorMongo> authorProcessor,
                            ItemWriter<AuthorMongo> authorWriter) {
        return stepBuilderFactory.get("stepAuthors")
                .<AuthorSql, AuthorMongo> chunk(5)
                .reader(authorItemReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepGenres(RepositoryItemReader<GenreSql> genreItemReader,
                            ItemProcessor<GenreSql, GenreMongo> genreProcessor,
                            ItemWriter<GenreMongo> genreWriter) {
        return stepBuilderFactory.get("stepGenres")
                .<GenreSql, GenreMongo> chunk(5)
                .reader(genreItemReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepBooks(RepositoryItemReader<BookSql> bookItemReader,
                          ItemProcessor<BookSql, BookMongo> bookProcessor,
                          ItemWriter<BookMongo> bookWriter) {
        return stepBuilderFactory.get("stepBooks")
                .<BookSql, BookMongo> chunk(5)
                .reader(bookItemReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepComments(RepositoryItemReader<CommentSql> commentItemReader,
                          ItemProcessor<CommentSql, CommentMongo> commentProcessor,
                          ItemWriter<CommentMongo> commentWriter) {
        return stepBuilderFactory.get("stepComments")
                .<CommentSql, CommentMongo> chunk(5)
                .reader(commentItemReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .allowStartIfComplete(true)
                .build();
    }



    //ITEM READERS
    @Bean
    public RepositoryItemReader<AuthorSql> authorItemReader() {
        return new RepositoryItemReaderBuilder<AuthorSql>()
                .repository(authorSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<GenreSql> genreItemReader() {
        return new RepositoryItemReaderBuilder<GenreSql>()
                .repository(genreSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<BookSql> bookItemReader() {
        return new RepositoryItemReaderBuilder<BookSql>()
                .repository(bookSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<CommentSql> commentItemReader() {
        return new RepositoryItemReaderBuilder<CommentSql>()
                .repository(commentSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }



    //ITEM PROCESSORS
    @Bean
    public ItemProcessor<AuthorSql, AuthorMongo> authorProcessor() {
        return convertService::authorToMongo;
    }

    @Bean
    public ItemProcessor<GenreSql, GenreMongo> genreProcessor() {
        return convertService::genreToMongo;
    }

    @Bean
    public ItemProcessor<BookSql, BookMongo> bookProcessor() { return convertService::bookToMongo; }

    @Bean
    public ItemProcessor<CommentSql, CommentMongo> commentProcessor() { return convertService::commentToMongo; }



    //ITEM WRITERS
    @Bean
    public MongoItemWriter<AuthorMongo> authorWriter() {
        return new MongoItemWriterBuilder<AuthorMongo>()
                .collection("authors")
                .template(mt)
                .build();
    }

    @Bean
    public MongoItemWriter<GenreMongo> genreWriter() {
        return new MongoItemWriterBuilder<GenreMongo>()
                .collection("genres")
                .template(mt)
                .build();
    }

    @Bean
    public MongoItemWriter<BookMongo> bookWriter() {
        return new MongoItemWriterBuilder<BookMongo>()
                .collection("books")
                .template(mt)
                .build();
    }

    @Bean
    public MongoItemWriter<CommentMongo> commentWriter() {
        return new MongoItemWriterBuilder<CommentMongo>()
                .collection("comment")
                .template(mt)
                .build();
    }
}
