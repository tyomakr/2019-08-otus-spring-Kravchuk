package ru.otus.spring.library.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.library.webflux.domain.Book;
import ru.otus.spring.library.webflux.domain.Comment;
import ru.otus.spring.library.webflux.repository.AuthorRepository;
import ru.otus.spring.library.webflux.repository.BookRepository;
import ru.otus.spring.library.webflux.repository.CommentRepository;
import ru.otus.spring.library.webflux.repository.GenreRepository;
import ru.otus.spring.library.webflux.rest.dto.AuthorDto;
import ru.otus.spring.library.webflux.rest.dto.BookDto;
import ru.otus.spring.library.webflux.rest.dto.GenreDto;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Spring11App {

    public static void main(String[] args) {
        SpringApplication.run(Spring11App.class);
    }


    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository bookRepository,
                                                            AuthorRepository authorRepository,
                                                            GenreRepository genreRepository,
                                                            CommentRepository commentRepository) {
        return route()
                .GET("/api/v1/books", accept(APPLICATION_JSON),
                        request -> ok().body(bookRepository.findAll().map(BookDto::toDto)
                                .collect(Collectors.toList()), BookDto.class))
                .GET("/api/v1/books/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                        .flatMap(book -> ok().contentType(APPLICATION_JSON).bodyValue(book))
                        .switchIfEmpty(notFound().build())
                )
                .POST("/api/v1/books/", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(request.bodyToMono(BookDto.class)
                                        .flatMap(bookDto -> bookRepository.saveBook(BookDto.toDomain(bookDto))), Book.class)
                )
                .PUT("/api/v1/books/", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(request.bodyToMono(Book.class)
                                        .flatMap(bookRepository::saveBook), Book.class)
                )
                .DELETE("/api/v1/books/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(request.pathVariable("id"))
                            .flatMap(book -> ok().contentType(APPLICATION_JSON).build())
                 )

                .GET("/api/v1/books/{id}/comments", accept(APPLICATION_JSON),
                        request -> ok().body(commentRepository.findAllByBook_Id(request.pathVariable("id")), Comment.class)

                )
                .GET("/api/v1/authors", accept(APPLICATION_JSON), request -> ok().body(authorRepository.findAll(), AuthorDto.class))
                .GET("/api/v1/genres", accept(APPLICATION_JSON), request -> ok().body(genreRepository.findAll(), GenreDto.class))
                .build();
    }
}
