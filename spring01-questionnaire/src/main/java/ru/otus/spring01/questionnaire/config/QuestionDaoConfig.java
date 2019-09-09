package ru.otus.spring01.questionnaire.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring01.questionnaire.dao.QuestionDao;
import ru.otus.spring01.questionnaire.dao.QuestionDaoImpl;

@Configuration
public class QuestionDaoConfig {

    @Value("${question.file}")
    String questionFile;

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoImpl(questionFile);
    }
}
