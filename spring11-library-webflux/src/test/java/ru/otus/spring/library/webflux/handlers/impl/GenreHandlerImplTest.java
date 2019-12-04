package ru.otus.spring.library.webflux.handlers.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import ru.otus.spring.library.webflux.domain.Genre;
import ru.otus.spring.library.webflux.repository.GenreRepository;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("GenreHandler должен")
class GenreHandlerImplTest {

    private static final Genre TEST_GENRE = new Genre("id_id_id_22", "TEST GENRE");
    private WebTestClient webTestClient;

    @Autowired
    private RouterFunction routerFunction;

    @MockBean
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
        given(this.genreRepository.findAll()).willReturn(Flux.just(TEST_GENRE));
    }

    @Test
    @DisplayName("получать список жанров")
    void shouldGetAllGenres() {

        webTestClient.get().uri("/api/v1/genres/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(TEST_GENRE.getId());
    }
}