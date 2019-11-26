package ru.otus.spring.library.webmvc.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с авторами должен")
class AuthorControllerTest {

    private static final String TEST_AUTHOR_NAME = "Джон Рональд Руэл Толкин";


    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("возвращать rest со списком авторов")
    void getAllAuthors() throws Exception {
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].authorName", is(TEST_AUTHOR_NAME)))
                .andDo(print())
                .andReturn();
    }
}