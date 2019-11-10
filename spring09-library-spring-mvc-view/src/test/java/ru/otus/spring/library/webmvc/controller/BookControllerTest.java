package ru.otus.spring.library.webmvc.controller;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.repository.BookRepository;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.GenreService;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с книгами должен")
class BookControllerTest {

    private static final String TEST_BOOK_TITLE_1 = "AAA";
    private static final String TEST_AUTHOR_NAME = "BBB";
    private static final String TEST_GENRE_TITLE = "CCC";
    private static final String TEST_BOOK_TITLE_2 = "Властелин колец";
    private static final String TEST_BOOK_TITLE_3 = "First Head Java";
    private static final String TEST_BOOK_TITLE_4 = "Сильмариллион";
    private static final String TEST_BOOK_TITLE_5 = "Гиперион";
    private static final String TEST_BOOK_TITLE_6 = "TEST_TITLE_RENAMED";

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("возвращать страницу со списком книг")
    void getBooksListPage() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("booksList")))
                .andReturn();
    }

    @Test
    @DisplayName("при успешном создании книги перенаправить на страницу списка книг")
    void createBook() throws Exception {
        mockMvc.perform(post("/books/create")
                .param("title", TEST_BOOK_TITLE_1)
                .param("author", TEST_AUTHOR_NAME)
                .param("genre", TEST_GENRE_TITLE))
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @DisplayName("возвращать страницу редактирования книги")
    void editBook() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_2);
        mockMvc.perform(get("/books/edit/?id={id}", book.get().getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Редактирование книги")))
                .andReturn();
    }

    @Test
    @DisplayName("возвращать страницу после изменения поля название книги")
    void updateTitle() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_4);
        book.get().setTitle(TEST_BOOK_TITLE_6);
        mockMvc.perform(post("/books/edit/title")
            .flashAttr("bookFromForm", book))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
    }

    @Test
    @DisplayName("возвращать страницу, после удаления автора из книги")
    void removeBookAuthor() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_3);
        val bSize = book.get().getAuthors().size();
        val bookId = book.get().getId();
        val authorId = book.get().getAuthors().get(0).getId();
        mockMvc.perform(post("/books/edit/{id}/author/delete/{authorId}", bookId, authorId))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();

        val aSize = bookRepository.findByTitle(TEST_BOOK_TITLE_3).get().getAuthors().size();
        Assert.assertEquals(bSize - 1, aSize);
    }

    @Test
    @DisplayName("возвращать страницу, после удаления жанра из книги")
    void removeBookGenre() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_3);
        val bSize = book.get().getGenres().size();
        val bookId = book.get().getId();
        val genreId = book.get().getGenres().get(0).getId();
        mockMvc.perform(post("/books/edit/{id}/genre/delete/{genreId}", bookId, genreId))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();

        val aSize = bookRepository.findByTitle(TEST_BOOK_TITLE_3).get().getGenres().size();
        Assert.assertEquals(bSize - 1, aSize);
    }

    @Test
    @DisplayName("возвратить страницу, после добаления автора в книгу")
    void addBookAuthor() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_3);
        val bSize = book.get().getAuthors().size();
        val bookId = book.get().getId();
        val authorId = authorService.findOrCreateAuthor(TEST_AUTHOR_NAME).getId();
        mockMvc.perform(post("/books/edit/{id}/author/add", bookId)
            .param("authorId", authorId))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
        val aSize = bookRepository.findByTitle(TEST_BOOK_TITLE_3).get().getAuthors().size();
        Assert.assertEquals(bSize + 1, aSize);
    }

    @Test
    @DisplayName("возвратить страницу, после добаления жанра в книгу")
    void addBookGenre() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_3);
        val bSize = book.get().getGenres().size();
        val bookId = book.get().getId();
        val genreId = genreService.findOrCreateGenre(TEST_GENRE_TITLE).getId();
        mockMvc.perform(post("/books/edit/{id}/genre/add", bookId)
                .param("genreId", genreId))
                .andExpect(redirectedUrl("/books/edit?id=" + book.get().getId()))
                .andReturn();
        val aSize = bookRepository.findByTitle(TEST_BOOK_TITLE_3).get().getGenres().size();
        Assert.assertEquals(bSize + 1, aSize);
    }

    @Test
    @DisplayName("возвратить страницу со списком книг после удаления книги")
    void removeBook() throws Exception {
        val book = bookRepository.findByTitle(TEST_BOOK_TITLE_5);
        val bSize = bookRepository.findAll().size();
        mockMvc.perform(post("/books/remove/{id}", book.get().getId()))
                .andExpect(redirectedUrl("/books"))
                .andReturn();

        val aSize = bookRepository.findAll().size();
        Assert.assertEquals(bSize - 1, aSize);
    }
}