package ru.otus.spring.library.webmvc.rest;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.repository.BookRepository;
import ru.otus.spring.library.webmvc.service.BookService;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с книгами должен")
class BookControllerTest {

    private static final String TEST_BOOK_1_TITLE = "Властелин колец";
    private static final String TEST_BOOK_2_TITLE = "TEST BOOK";
    private static final String TEST_BOOK_3_TITLE = "Сильмариллион";
    private static final String TEST_BOOK_4_TITLE = "Гиперион";
    private static final String TEST_AUTHOR_1_NAME = "Джон Рональд Руэл Толкин";
    private static final String TEST_AUTHOR_2_NAME = "TEST AUTHOR";
    private static final String TEST_GENRE_1_TITLE = "Фэнтези";
    private static final String TEST_GENRE_2_TITLE = "TEST GENRE";
    private static final String TEST_COMMENT_TEXT = "Sample comment 1";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("возвращать rest со списком книг")
    void getAllBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is(TEST_BOOK_1_TITLE)))
                .andExpect(jsonPath("$[0].authors", is(TEST_AUTHOR_1_NAME)))
                .andExpect(jsonPath("$[0].genres", is(TEST_GENRE_1_TITLE)))
                .andDo(print())
                .andReturn();
    }


    @Test
    @DisplayName("создавать новую книгу")
    void createBook() throws Exception {

        mockMvc.perform(post("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"" + TEST_BOOK_2_TITLE + "\",\"authors\":\"" + TEST_AUTHOR_2_NAME + "\",\"genres\":\"" + TEST_GENRE_2_TITLE + "\"}"))
                .andDo(print());

        Assert.assertEquals(bookRepository.findByTitle(TEST_BOOK_2_TITLE).get().getTitle(), TEST_BOOK_2_TITLE);
    }


    @Test
    @DisplayName("получать книгу по ID")
    void getBookById() throws Exception {

        Book testBook = bookRepository.findByTitle(TEST_BOOK_1_TITLE).get();
        val testBookId = testBook.getId();
        val authorId = testBook.getAuthors().get(0).getId();

        mockMvc.perform(get("/api/v1/books/" + testBookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(authorId)))
                .andDo(print());
    }


    @Test
    @DisplayName("сохранять изменения в книге в базу")
    void updateBook() throws Exception {

        val testBook = bookRepository.findByTitle(TEST_BOOK_3_TITLE).get();
        val testBookNewTitle = "RENAMED TITLE";
        testBook.setTitle(testBookNewTitle);

        mockMvc.perform(put("/api/v1/books/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + testBook.getId() + "\"," +
                        "\"title\":\"" + testBookNewTitle + "\"," +
                        "\"authors\":[{\"id\":\"" + testBook.getAuthors().get(0).getId() + "\"," +
                        "\"authorName\":\"" + TEST_AUTHOR_1_NAME +"\"}]," +
                        "\"genres\":[{\"id\":\"" + testBook.getGenres().get(0).getId() + "\"," +
                        "\"genreTitle\":\"" + TEST_GENRE_1_TITLE + "\"}]}"))
                .andExpect(status().isOk())
                .andDo(print());

        Assert.assertEquals(testBookNewTitle, bookRepository.findById(testBook.getId()).get().getTitle());
    }


    @Test
    @DisplayName("удалить книгу с заданным ИД")
    void deleteBook() throws Exception {

        val book = bookRepository.findByTitle(TEST_BOOK_4_TITLE);
        val bSize = bookRepository.findAll().size();
        mockMvc.perform(delete("/api/v1/books/" + book.get().getId()))
                .andExpect(status().isOk())
                .andDo(print());

        val aSize = bookRepository.findAll().size();
        Assert.assertEquals(bSize - 1, aSize);
    }


    @Test
    @DisplayName("получить комментарии к книге")
    void getBookCommentsByBookId() throws Exception {

        val book = bookRepository.findByTitle(TEST_BOOK_1_TITLE);

        mockMvc.perform(get("/api/v1/books/" + book.get().getId() + "/comments/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_COMMENT_TEXT)))
                .andDo(print());
    }
}