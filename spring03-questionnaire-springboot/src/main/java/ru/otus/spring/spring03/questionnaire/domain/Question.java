package ru.otus.spring.spring03.questionnaire.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {

    String question;
    String correctAnswer;

}
