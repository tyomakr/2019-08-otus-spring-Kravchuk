package ru.otus.spring.library.webflux.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.handlers.AuthorHandler;
import ru.otus.spring.library.webflux.repository.AuthorRepository;
import ru.otus.spring.library.webflux.rest.dto.AuthorDto;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AuthorHandlerImpl implements AuthorHandler {

    private final AuthorRepository authorRepository;


    @Override
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().body(authorRepository.findAll(), AuthorDto.class);
    }
}
