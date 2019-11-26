package ru.otus.spring.library.webmvc.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с жанрами должен")
class GenreControllerTest {

    private static final String TEST_GENRE_TITLE = "Фэнтези";


    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("возвращать rest со списком жанров")
    void getAllGenres() throws Exception {
        mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].genreTitle", is(TEST_GENRE_TITLE)))
                .andDo(print())
                .andReturn();
    }
}