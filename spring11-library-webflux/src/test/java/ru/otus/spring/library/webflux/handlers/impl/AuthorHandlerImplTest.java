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
import ru.otus.spring.library.webflux.domain.Author;
import ru.otus.spring.library.webflux.repository.AuthorRepository;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("AuthorHandler должен")
class AuthorHandlerImplTest {

    private static final Author TEST_AUTHOR = new Author("id_id_id_11", "TEST AUTHOR TEST");
    private WebTestClient webTestClient;

    @Autowired
    private RouterFunction routerFunction;

    @MockBean
    private AuthorRepository authorRepository;


    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
        given(this.authorRepository.findAll()).willReturn(Flux.just(TEST_AUTHOR));
    }


    @Test
    @DisplayName("получать список авторов")
    void shouldGetAllAuthors() {

        webTestClient.get().uri("/api/v1/authors/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(TEST_AUTHOR.getId());
    }
}