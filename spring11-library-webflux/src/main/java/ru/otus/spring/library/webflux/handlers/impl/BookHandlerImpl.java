package ru.otus.spring.library.webflux.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.domain.Book;
import ru.otus.spring.library.webflux.domain.Comment;
import ru.otus.spring.library.webflux.handlers.BookHandler;
import ru.otus.spring.library.webflux.repository.BookRepository;
import ru.otus.spring.library.webflux.repository.CommentRepository;
import ru.otus.spring.library.webflux.rest.dto.BookDto;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class BookHandlerImpl implements BookHandler {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    @Override
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().body(bookRepository.findAll()
                .map(BookDto::toDto).collect(Collectors.toList()), BookDto.class);
    }

    @Override
    public Mono<ServerResponse> findById(ServerRequest request) {
        return bookRepository.findById(request.pathVariable("id"))
                .flatMap(book -> ok().contentType(APPLICATION_JSON).bodyValue(book))
                .switchIfEmpty(notFound().build());
    }

    @Override
    public Mono<ServerResponse> createBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .flatMap(bookDto -> bookRepository.saveBook(BookDto.toDomain(bookDto)))
                .flatMap(book -> created(request.uriBuilder().pathSegment(book.getId()).build())
                        .contentType(APPLICATION_JSON)
                        .body(fromValue(book)));
    }

    @Override
    public Mono<ServerResponse> updateBook(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Book.class)
                .flatMap(bookRepository::saveBook), Book.class);
    }

    @Override
    public Mono<ServerResponse> deleteBook(ServerRequest request) {
        return noContent().build(bookRepository.deleteById(request.pathVariable("id")));
    }

    @Override
    public Mono<ServerResponse> findCommentsByBookId(ServerRequest request) {
        return ok().body(commentRepository.findAllByBook_Id(request.pathVariable("id")), Comment.class);
    }
}