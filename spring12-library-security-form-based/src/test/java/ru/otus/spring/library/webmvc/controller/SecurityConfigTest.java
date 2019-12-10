package ru.otus.spring.library.webmvc.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.handler.AccessDeniedHandlerImpl;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.CommentService;
import ru.otus.spring.library.webmvc.service.GenreService;
import ru.otus.spring.library.webmvc.service.impl.CustomUserDetailService;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Spring Security должен")
@WebMvcTest(controllers = {BookController.class})
@Import({AccessDeniedHandlerImpl.class})
public class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;
    @MockBean
    AuthorService authorService;
    @MockBean
    GenreService genreService;
    @MockBean
    CommentService commentService;
    @MockBean
    BookController bookController;
    @MockBean
    CustomUserDetailService customUserDetailService;



    @Test
    @DisplayName("при анонимном доступе, вернуть страницу логина для списка книг")
    void noAccessUnauthorizedUser() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/login")))
                .andExpect(unauthenticated());
    }


    @ParameterizedTest
    @ValueSource(strings = {"/books/edit", "/books/create", "/books/remove/" + "testBookId"})
    @DisplayName("запретить доступ пользователю с ROLE_USER, в ответ на запросы, разрешенные только для ROLE_ADMIN")
    @WithMockUser(authorities = "ROLE_USER")
    void forbiddenRoleUserParamTest(String apiUrl) throws Exception {

        mockMvc.perform(post(apiUrl))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/error-403"))
                .andReturn();
    }
}