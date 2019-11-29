package ru.otus.spring.library.webmvc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.service.AuthorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthorController.class)
@DisplayName("Контроллер для работы с авторами должен")
class AuthorControllerTest {


    private static List<Author> authorList = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;



    @BeforeEach
    void setUp() {
        Author TEST_AUTHOR = new Author("id_id_id_id", "TEST AUTHOR TEST");
        authorList = Collections.singletonList(TEST_AUTHOR);
        given(this.authorService.findAll()).willReturn(authorList);
    }


    @Test
    @DisplayName("возвращать rest со списком авторов")
    void getAllAuthors() throws Exception {
        mockMvc.perform(get("/api/v1/authors")
        .content(objectMapper.writeValueAsString(authorList)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value(authorList.get(0)))
                .andDo(print())
                .andReturn();
    }
}