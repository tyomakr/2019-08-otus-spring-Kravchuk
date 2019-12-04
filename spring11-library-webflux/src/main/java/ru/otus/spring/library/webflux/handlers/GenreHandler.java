package ru.otus.spring.library.webflux.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface GenreHandler {

    Mono<ServerResponse> findAll(ServerRequest request);
}
