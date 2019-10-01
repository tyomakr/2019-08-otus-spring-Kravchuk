package ru.otus.spring.library.springjdbc.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.library.springjdbc.dao.impl.AuthorDaoJdbc;
import ru.otus.spring.library.springjdbc.domain.Author;
import ru.otus.spring.library.springjdbc.service.IOService;

@ExtendWith(SpringExtension.class)
@JdbcTest
@DisplayName("Сервис для работы с авторами")
class AuthorServiceImplTest {


    @SpyBean
    private AuthorDaoJdbc authorDao;
    @SpyBean
    private AuthorServiceImpl authorService;

    @MockBean
    private IOService ioService;



    @Test
    void isAuthorExists() {
        Author testAuthor = authorDao.getById(1);
        Assert.assertTrue(authorService.isAuthorExists(testAuthor));
    }
}