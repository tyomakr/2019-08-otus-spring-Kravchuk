package ru.otus.spring.spring03.questionnaire.dao;

import ru.otus.spring.spring03.questionnaire.domain.Question;

import java.util.List;
import java.util.Locale;

public interface QuestionDao {

    List<Question> getQuestionList(Locale locale);

}
