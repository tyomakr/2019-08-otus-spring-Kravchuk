package ru.otus.spring.library.webflux.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BookHandler {

    Mono<ServerResponse> findAll(ServerRequest request);

    Mono<ServerResponse> findById(ServerRequest request);

    Mono<ServerResponse> createBook(ServerRequest request);

    Mono<ServerResponse> updateBook(ServerRequest request);

    Mono<ServerResponse> deleteBook(ServerRequest request);

    Mono<ServerResponse> findCommentsByBookId(ServerRequest request);
}
