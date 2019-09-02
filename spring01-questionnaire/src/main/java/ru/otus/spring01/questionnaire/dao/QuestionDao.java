package ru.otus.spring01.questionnaire.dao;

import ru.otus.spring01.questionnaire.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestionList();

}
