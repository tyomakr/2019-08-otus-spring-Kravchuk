package ru.otus.spring.library.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findGenreByGenreTitle(String genreTitle);
}