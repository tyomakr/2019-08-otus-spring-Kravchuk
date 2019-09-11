package ru.otus.spring01.questionnaire.dao;

import ru.otus.spring01.questionnaire.domain.Question;

import java.util.List;
import java.util.Locale;

public interface QuestionDao {

    List<Question> getQuestionList();

    void setLocale(Locale locale);
}
