package ru.otus.spring.library.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.library.webflux.handlers.AuthorHandler;
import ru.otus.spring.library.webflux.handlers.BookHandler;
import ru.otus.spring.library.webflux.handlers.GenreHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EndpointConfiguration {

    @Bean
    RouterFunction<ServerResponse> endpointRoutes (BookHandler bookHandler, AuthorHandler authorHandler, GenreHandler genreHandler) {
        return RouterFunctions
                .route(GET("/api/v1/books").and(accept(MediaType.APPLICATION_JSON)), bookHandler::findAll)
                .andRoute(GET("/api/v1/books/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::findById)
                .andRoute(POST("/api/v1/books/").and(accept(MediaType.APPLICATION_JSON)), bookHandler::createBook)
                .andRoute(PUT("/api/v1/books/").and(accept(MediaType.APPLICATION_JSON)), bookHandler::updateBook)
                .andRoute(DELETE("/api/v1/books/{id}").and(accept(MediaType.APPLICATION_JSON)), bookHandler::deleteBook)
                .andRoute(GET("/api/v1/books/{id}/comments/").and(accept(MediaType.APPLICATION_JSON)), bookHandler::findCommentsByBookId)
                .andRoute(GET("/api/v1/authors/").and(accept(MediaType.APPLICATION_JSON)), authorHandler::findAll)
                .andRoute(GET("/api/v1/genres/").and(accept(MediaType.APPLICATION_JSON)), genreHandler::findAll);
    }
}
