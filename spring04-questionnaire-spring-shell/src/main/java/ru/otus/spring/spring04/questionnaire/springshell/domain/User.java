package ru.otus.spring.spring04.questionnaire.springshell.domain;

import lombok.Data;

@Data
public class User {

    private String firstName;
    private String lastName;
    private int grade;
    private boolean isTestCompleted = false;

    public String getUserName() {
        return firstName + " " + lastName;
    }

}
