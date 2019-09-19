package ru.otus.spring.spring04.questionnaire.springshell;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.spring04.questionnaire.springshell.dao.QuestionDaoImpl;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties =  {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
@DisplayName("Класс QuestionDaoImpl")
class QuestionDaoImplTest {

    @Autowired
    private QuestionDaoImpl questionDao;

    @Test
    @DisplayName("корректно получает список вопросов")
    void getQuestionValidList() {
        Assert.assertEquals(questionDao.getQuestionList().size(), 5);
        System.out.println(questionDao.getQuestionList().size());
    }
}