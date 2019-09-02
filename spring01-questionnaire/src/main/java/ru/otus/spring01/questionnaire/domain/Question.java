package ru.otus.spring01.questionnaire.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {

    String question;
    String correctAnswer;

}
