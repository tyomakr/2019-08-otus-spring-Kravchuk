package ru.otus.spring.library.webmvc.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.repository.BookRepository;
import ru.otus.spring.library.webmvc.service.*;
import ru.otus.spring.library.webmvc.service.impl.CustomUserDetailService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest({BookController.class})
@DisplayName("Контроллер для работы с книгами должен")
class BookControllerTest {

    private static final Author TEST_BOOK_AUTHOR_1 = new Author("a_id_1","TEST_AUTHOR");
    private static final Author TEST_BOOK_AUTHOR_2 = new Author("a_id_2","TEST_AUTHOR_2");
    private static final Genre TEST_BOOK_GENRE_1 = new Genre("g_id_1","TEST GENRE");
    private static final Genre TEST_BOOK_GENRE_2 = new Genre("g_id_2","TEST_GENRE_2");
    private static final Book TEST_BOOK = new Book("ID_ID_ID_ID","TEST_BOOK_TITLE", Collections.singletonList(TEST_BOOK_AUTHOR_1), Collections.singletonList(TEST_BOOK_GENRE_1));
    private static final Book TEST_BOOK_2 = new Book("ID_ID_ID_ID_2", "TEST_BOOK_2_TITLE", Arrays.asList(TEST_BOOK_AUTHOR_1, TEST_BOOK_AUTHOR_2), Arrays.asList(TEST_BOOK_GENRE_1, TEST_BOOK_GENRE_2));
    private static List<Book> BOOK_LIST = Collections.singletonList(TEST_BOOK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private MsgService msgService;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @MockBean
    private AccessDeniedHandler accessDeniedHandler;
    @MockBean
    private AclService aclService;


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу со списком книг")
    void getBooksListPage() throws Exception {

        given(bookService.findAll()).willReturn(BOOK_LIST);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("booksList")))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("при успешном создании книги перенаправить на страницу списка книг")
    void createBook() throws Exception {

        given(bookService.insertBook(
                TEST_BOOK.getTitle(), TEST_BOOK_AUTHOR_1.getAuthorName(), TEST_BOOK_GENRE_1.getGenreTitle()))
                .willReturn(TEST_BOOK);

        mockMvc.perform(post("/books/create")
                .param("title", TEST_BOOK.getTitle())
                .param("author", TEST_BOOK_AUTHOR_1.getAuthorName())
                .param("genre", TEST_BOOK_GENRE_1.getGenreTitle()))
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу редактирования книги пользователям с ROLE_ADMIN")
    void editBook() throws Exception {

        given(bookService.findById(TEST_BOOK.getId())).willReturn(Optional.of(TEST_BOOK));


        mockMvc.perform(get("/books/edit/?id={id}", TEST_BOOK.getId()))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Редактирование книги")))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("возвращать страницу просмотра книги пользователям с ROLE_USER")
    void viewBook() throws Exception {

        given(bookService.findById(TEST_BOOK.getId())).willReturn(Optional.of(TEST_BOOK));

        mockMvc.perform(get("/books/view/?id={id}", TEST_BOOK.getId()))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Просмотр книги")))
                .andDo(print())
                .andReturn();
    }


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу после изменения поля название книги")
    void updateTitle() throws Exception {

        given(bookRepository.findByTitle(TEST_BOOK.getTitle())).willReturn(Optional.of(TEST_BOOK));

        val book = bookRepository.findByTitle(TEST_BOOK.getTitle());
        book.get().setTitle(TEST_BOOK.getTitle());
        mockMvc.perform(post("/books/edit/title")
            .flashAttr("bookFromForm", book))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу, после удаления автора из книги")
    void removeBookAuthor() throws Exception {

        given(bookService.findById(TEST_BOOK_2.getId())).willReturn(Optional.of(TEST_BOOK_2));

        val book = bookService.findById(TEST_BOOK_2.getId());
        mockMvc.perform(post("/books/edit/{id}/author/delete/{authorId}",
                TEST_BOOK_2.getId(), TEST_BOOK_2.getAuthors().get(0).getId()))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу, после удаления жанра из книги")
    void removeBookGenre() throws Exception {

        given(bookService.findById(TEST_BOOK_2.getId())).willReturn(Optional.of(TEST_BOOK_2));

        val book = bookService.findById(TEST_BOOK_2.getId());
        mockMvc.perform(post("/books/edit/{id}/genre/delete/{genreId}",
                TEST_BOOK_2.getId(), TEST_BOOK_2.getGenres().get(0).getId()))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвратить страницу, после добаления автора в книгу")
    void addBookAuthor() throws Exception {

        given(bookService.findById(TEST_BOOK_2.getId())).willReturn(Optional.of(TEST_BOOK_2));
        Author author = new Author("a_id_3", "TA3");

        val book = bookService.findById(TEST_BOOK_2.getId());
        mockMvc.perform(post("/books/edit/{id}/author/add", TEST_BOOK_2.getId())
                .param("authorId", author.getId()))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвратить страницу, после добаления жанра в книгу")
    void addBookGenre() throws Exception {

        given(bookService.findById(TEST_BOOK_2.getId())).willReturn(Optional.of(TEST_BOOK_2));
        Genre genre = new Genre("g_id_3", "TG3");

        val book = bookService.findById(TEST_BOOK_2.getId());
        mockMvc.perform(post("/books/edit/{id}/genre/add", TEST_BOOK_2.getId())
                .param("genreId", genre.getId()))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвратить страницу со списком книг после удаления книги")
    void removeBook() throws Exception {

        mockMvc.perform(post("/books/remove/{id}", TEST_BOOK.getId()))
                .andExpect(redirectedUrl("/books"))
                .andReturn();
    }
}