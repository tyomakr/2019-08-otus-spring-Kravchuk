package ru.otus.spring.spring04.questionnaire.springshell.dao;

import ru.otus.spring.spring04.questionnaire.springshell.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestionList();
}
