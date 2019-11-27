package ru.otus.spring.library.webmvc.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.service.AuthorService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с авторами должен")
class AuthorControllerTest {

    private static final Author TEST_AUTHOR = new Author("id_id_id_id", "TEST AUTHOR TEST");
    private List<Author> authorList = Collections.singletonList(TEST_AUTHOR);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;


    @BeforeEach
    void setUp() {
        given(this.authorService.findAll()).willReturn(authorList);
    }


    @Test
    @DisplayName("возвращать rest со списком авторов")
    void getAllAuthors() throws Exception {
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorName", is(TEST_AUTHOR.getAuthorName())))
                .andDo(print())
                .andReturn();
    }
}