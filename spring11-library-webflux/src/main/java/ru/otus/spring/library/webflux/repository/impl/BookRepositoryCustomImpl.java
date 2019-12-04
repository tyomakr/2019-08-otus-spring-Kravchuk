package ru.otus.spring.library.webflux.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.domain.Book;
import ru.otus.spring.library.webflux.repository.AuthorRepository;
import ru.otus.spring.library.webflux.repository.BookRepositoryCustom;
import ru.otus.spring.library.webflux.repository.GenreRepository;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ReactiveMongoTemplate mongoTemplate;


    @Override
    public Mono<Book> saveBook(Book book) {
        return Mono.just(book)
                .flatMap(bk -> Flux.fromIterable(bk.getAuthors())
                        .flatMap(author -> authorRepository.findAuthorByAuthorName(author.getAuthorName())
                                .switchIfEmpty(Mono.defer(() -> authorRepository.save(author))))
                        .collectList().doOnNext(bk::setAuthors)
                        .thenReturn(bk))
                .flatMap(bk -> Flux.fromIterable(bk.getGenres())
                        .flatMap(genre -> genreRepository.findGenreByGenreTitle(genre.getGenreTitle())
                                .switchIfEmpty(Mono.defer(() -> genreRepository.save(genre))))
                        .collectList().doOnNext(bk::setGenres)
                        .thenReturn(bk))

                .flatMap(mongoTemplate::save);
    }
}
