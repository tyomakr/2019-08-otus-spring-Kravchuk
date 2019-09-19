package ru.otus.spring.spring04.questionnaire.springshell.service;

import ru.otus.spring.spring04.questionnaire.springshell.domain.User;

public interface UserService {

    User createUser();

    void setGrade(int grade);

}
