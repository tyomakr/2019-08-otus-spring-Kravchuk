package ru.otus.spring.library.webflux.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.handlers.GenreHandler;
import ru.otus.spring.library.webflux.repository.GenreRepository;
import ru.otus.spring.library.webflux.rest.dto.GenreDto;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class GenreHandlerImpl implements GenreHandler {

    private final GenreRepository genreRepository;

    @Override
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().body(genreRepository.findAll(), GenreDto.class);
    }
}
