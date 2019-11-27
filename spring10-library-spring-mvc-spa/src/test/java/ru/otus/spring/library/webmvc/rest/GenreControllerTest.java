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
import ru.otus.spring.library.webmvc.domain.Genre;
import ru.otus.spring.library.webmvc.service.GenreService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GenreController.class)
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с жанрами должен")
class GenreControllerTest {

    private static final Genre TEST_GENRE = new Genre("id_id_id_id", "TEST GENRE TEST");
    private List<Genre> genreList = Collections.singletonList(TEST_GENRE);


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;


    @BeforeEach
    void setUp() {
        given(this.genreService.findAll()).willReturn(genreList);
    }


    @Test
    @DisplayName("возвращать rest со списком жанров")
    void getAllGenres() throws Exception {
        mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].genreTitle", is(TEST_GENRE.getGenreTitle())))
                .andDo(print())
                .andReturn();
    }
}