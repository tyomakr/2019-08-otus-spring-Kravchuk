package ru.otus.spring.spring03.questionnaire.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.spring03.questionnaire.service.QuestionServiceImpl;

import java.util.Locale;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Класс QuestionDaoImpl")
class QuestionDaoImplTest {

    @Autowired
    private QuestionDaoImpl questionDao;

    @MockBean
    private QuestionServiceImpl questionService = null;

    private Locale locale = new Locale("ru", "RU");



    @Test
    @DisplayName("корректно получает список вопросов")
    void getQuestionValidList() {
        Assert.assertEquals(questionDao.getQuestionList(locale).size(), 5);
        System.out.println(questionDao.getQuestionList(locale).size());
    }
}