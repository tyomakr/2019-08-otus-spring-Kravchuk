package ru.otus.spring.library.webmvc.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.library.webmvc.repository.BookRepository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с книгами (c учетом ACL) должен")
public class BookControllerTestWithACL {

    private static final int QTY_ALL_BOOKS = 34;
    private static final int QTY_PUBLIC_BOOKS = 32;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;


    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("возвращать страницу со списком всех книг для админа")
    void getBooksListPageForRoleAdmin() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("booksList")))
                .andExpect(model().attribute("bookList", hasSize(QTY_ALL_BOOKS)))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("возвращать страницу со списком доступных книг для пользователя")
    void getBooksListPageForRoleUser() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("booksList")))
                .andExpect(model().attribute("bookList", hasSize(QTY_PUBLIC_BOOKS)))
                .andDo(print())
                .andReturn();
    }
}
