package ru.otus.spring.library.webflux;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.webflux.domain.Author;
import ru.otus.spring.library.webflux.domain.Book;
import ru.otus.spring.library.webflux.domain.Comment;
import ru.otus.spring.library.webflux.domain.Genre;
import ru.otus.spring.library.webflux.repository.AuthorRepository;
import ru.otus.spring.library.webflux.repository.BookRepository;
import ru.otus.spring.library.webflux.repository.CommentRepository;
import ru.otus.spring.library.webflux.repository.GenreRepository;
import ru.otus.spring.library.webflux.rest.dto.BookDto;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(Spring11App.class)
@DisplayName("RouterFunction должен")
class RouterFunctionTest {

    private static final Author TEST_AUTHOR = new Author("id_id_id_11", "TEST AUTHOR TEST");
    private static final Genre TEST_GENRE = new Genre("id_id_id_22", "TEST GENRE");
    private static final Book TEST_BOOK = new Book("id_id_id_33", "TEST BOOK", Collections.singletonList(TEST_AUTHOR), Collections.singletonList(TEST_GENRE));
    private static final Book TEST_BOOK_2 = new Book("id_id_id_44", "TEST BOOK DEL", Collections.singletonList(TEST_AUTHOR), Collections.singletonList(TEST_GENRE));
    private static final Book TEST_BOOK_3 = new Book("id_id_id_55","TEST BOOK CREATE", Collections.singletonList(TEST_AUTHOR), Collections.singletonList(TEST_GENRE));



    @Autowired
    private WebTestClient webTestClient;

//    @Autowired
//    private RouterFunction routerFunction;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        //webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
        given(this.authorRepository.findAll()).willReturn(Flux.just(TEST_AUTHOR));
        given(this.genreRepository.findAll()).willReturn(Flux.just(TEST_GENRE));
        given(this.bookRepository.findAll()).willReturn(Flux.just(TEST_BOOK, TEST_BOOK_2));
        given(this.bookRepository.findById(TEST_BOOK.getId())).willReturn(Mono.just(TEST_BOOK));

    }


    @Test
    @DisplayName("получать список авторов")
    void shouldGetAllAuthors() {

        webTestClient.get().uri("/api/v1/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(TEST_AUTHOR.getId());
    }

    @Test
    @DisplayName("получать список жанров")
    void shouldGetAllGenres() {

        webTestClient.get().uri("/api/v1/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(TEST_GENRE.getId());
    }

    @Test
    @DisplayName("получать список книг")
    void shouldGetAllBooks() {
        webTestClient.get().uri("/api/v1/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id_id_id_33");
    }

    @Test
    @DisplayName("получать книгу по id")
    void shouldGetBookById() {
        webTestClient.get().uri("/api/v1/books/{id}", TEST_BOOK.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(TEST_BOOK.getId());
    }

    @Test
    @DisplayName("создать новую книгу")
    void shouldCreateNewBook() {
        given(bookRepository.saveBook(any())).willReturn(Mono.just(TEST_BOOK_3));

        webTestClient.post().uri("/api/v1/books/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"title\":\"" + TEST_BOOK_3.getTitle() +
                        "\",\"authors\":\"" + TEST_BOOK_3.getAuthors().get(0).getAuthorName() +
                        "\",\"genres\":\"" + TEST_BOOK_3.getGenres().get(0).getGenreTitle() + "\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title", TEST_BOOK.getTitle());
    }

    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() {
        given(bookRepository.saveBook(any())).willReturn(Mono.just(TEST_BOOK_3));

        webTestClient.put().uri("/api/v1/books/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"id\":\"" + TEST_BOOK_3.getId() + "\"," +
                        "\"title\":\"" + TEST_BOOK_3.getTitle() + "\"," +
                        "\"authors\":[{\"id\":\"" + TEST_BOOK_3.getAuthors().get(0).getId() + "\"," +
                        "\"authorName\":\"" + TEST_BOOK_3.getAuthors().get(0).getAuthorName() +"\"}]," +
                        "\"genres\":[{\"id\":\"" + TEST_BOOK_3.getGenres().get(0).getId() + "\"," +
                        "\"genreTitle\":\"" + TEST_BOOK_3.getGenres().get(0).getGenreTitle() + "\"}]}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title", TEST_BOOK_3.getTitle());
    }

    @Test
    @DisplayName("удалить книгу по id")
    void shouldDeleteBookById() {
        given(bookRepository.deleteById(TEST_BOOK_2.getId())).willReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/books/{id}", TEST_BOOK_2.getId())
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    @DisplayName("показать комментарии по id книги")
    void shouldGetBookCommentsByBookId() {
        val comment = "SAMPLE";
        Flux<Comment> commentList = Flux.just(new Comment(comment, TEST_BOOK));

        given(commentRepository.findAllByBook_Id(TEST_BOOK.getId())).willReturn(commentList);

        webTestClient.get().uri("/api/v1/books/{id}/comments/", TEST_BOOK.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath(comment);
    }


}