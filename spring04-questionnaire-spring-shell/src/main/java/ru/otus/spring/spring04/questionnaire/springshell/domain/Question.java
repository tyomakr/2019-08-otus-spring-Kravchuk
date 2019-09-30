package ru.otus.spring.spring04.questionnaire.springshell.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {

    String question;
    String correctAnswer;

}
