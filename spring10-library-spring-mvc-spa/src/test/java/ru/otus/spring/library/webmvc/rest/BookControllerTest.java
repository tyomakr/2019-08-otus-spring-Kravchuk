package ru.otus.spring.library.webmvc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Comment;
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.CommentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BookController.class)
@DisplayName("Контроллер для работы с книгами должен")
class BookControllerTest {

    private static final List<Book> TBL = fillTestBookList();

    private static final String TB_ID = "ID_ID_ID_ID";
    private static final String TB_TITLE = "TEST_BOOK_TITLE";
    private static final String TB_AUTHOR = "TEST_BOOK_AUTHOR";
    private static final String TB_GENRE = "TEST_BOOK_GENRE";


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;



    @Test
    @DisplayName("возвращать rest со списком книг")
    void getAllBooks() throws Exception {

        given(this.bookService.findAll()).willReturn(TBL);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is(TBL.get(0).getTitle())))
                .andExpect(jsonPath("$[0].authors", is(TBL.get(0).getAuthors().get(0).getAuthorName())))
                .andExpect(jsonPath("$[0].genres", is(TBL.get(0).getGenres().get(0).getGenreTitle())))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("создавать новую книгу")
    void createBook() throws Exception {

        val bookCreated = new Book(TB_TITLE, new Author(TB_AUTHOR), new Genre(TB_GENRE));
        given(bookService.insertBook(TB_TITLE, TB_AUTHOR, TB_GENRE)).willReturn(bookCreated);

        mockMvc.perform(post("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"" + TB_TITLE +
                        "\",\"authors\":\"" + TB_AUTHOR +
                        "\",\"genres\":\"" + TB_GENRE + "\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("получать книгу по ID")
    void getBookById() throws Exception {

        val bookFind = new Book(TB_ID, TB_TITLE,
                Collections.singletonList(new Author(TB_AUTHOR)),
                Collections.singletonList(new Genre(TB_GENRE)));
        given(bookService.findById(TB_ID)).willReturn(java.util.Optional.of(bookFind));

        mockMvc.perform(get("/api/v1/books/" + bookFind.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(bookFind.getAuthors().get(0).getAuthorName())))
                .andDo(print());
    }


    @Test
    @DisplayName("сохранять изменения в книге в базу")
    void updateBook() throws Exception {

        val bookUpdated = new Book(TB_ID, TB_TITLE,
                Collections.singletonList(new Author(TB_AUTHOR)),
                Collections.singletonList(new Genre(TB_GENRE)));

        given(bookService.updateBook(bookUpdated)).willReturn(bookUpdated);

        mockMvc.perform(put("/api/v1/books/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + bookUpdated.getId() + "\"," +
                        "\"title\":\"" + bookUpdated.getTitle() + "\"," +
                        "\"authors\":[{\"id\":\"" + bookUpdated.getAuthors().get(0).getId() + "\"," +
                        "\"authorName\":\"" + bookUpdated.getAuthors().get(0).getAuthorName() +"\"}]," +
                        "\"genres\":[{\"id\":\"" + bookUpdated.getGenres().get(0).getId() + "\"," +
                        "\"genreTitle\":\"" + bookUpdated.getGenres().get(0).getGenreTitle() + "\"}]}"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("удалить книгу с заданным ИД")
    void deleteBook() throws Exception {

        val bookRemoved = new Book(TB_ID, TB_TITLE,
                Collections.singletonList(new Author(TB_AUTHOR)),
                Collections.singletonList(new Genre(TB_GENRE)));

        mockMvc.perform(delete("/api/v1/books/" + bookRemoved.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("получить комментарии к книге")
    void getBookCommentsByBookId() throws Exception {

        val bookWithComments = new Book(TB_ID, TB_TITLE,
                Collections.singletonList(new Author(TB_AUTHOR)),
                Collections.singletonList(new Genre(TB_GENRE)));

        val commentList = Collections.singletonList(new Comment("Sample comment 1", bookWithComments));

        given(bookService.findById(bookWithComments.getId())).willReturn(java.util.Optional.of(bookWithComments));
        given(commentService.findAllCommentsByBookId(bookWithComments.getId())).willReturn(commentList);

        mockMvc.perform(get("/api/v1/books/" + bookWithComments.getId() + "/comments/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(commentList.get(0).getCommentText())))
                .andDo(print());
    }


    private static List<Book> fillTestBookList() {
        List<Book> bl = new ArrayList<>();
        Author author1 = new Author("a_id_1", "TEST_AUTHOR_1_TEST");
        Author author2 = new Author("a_id_2", "TEST_AUTHOR_2_TEST");
        Genre genre1 = new Genre("g_id_1", "TEST_GENRE_1_TEST");
        Genre genre2 = new Genre("g_id_2", "TEST_GENRE_2_TEST");

        bl.add(new Book("id1", "TEST_BOOK_1", Collections.singletonList(author1), Collections.singletonList(genre1)));
        bl.add(new Book("id2", "TEST_BOOK_2", Collections.singletonList(author2), Collections.singletonList(genre2)));
        bl.add(new Book("id10", "TEST_DEL_BOOK", Collections.singletonList(author1), Collections.singletonList(genre2)));

        return bl;
    }
}