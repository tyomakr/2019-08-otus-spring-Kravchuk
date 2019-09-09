package ru.otus.spring01.questionnaire;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring01.questionnaire.service.QuestionService;

@Configuration
@ComponentScan
public class MainApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainApp.class);

        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.startQuestions();

    }
}
