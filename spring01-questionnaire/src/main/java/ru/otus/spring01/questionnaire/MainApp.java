package ru.otus.spring01.questionnaire;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring01.questionnaire.service.QuestionService;

public class MainApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");

        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.startQuestions();

    }
}
