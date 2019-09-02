package ru.otus.spring01.questionnaire.service;

import lombok.AllArgsConstructor;
import ru.otus.spring01.questionnaire.dao.QuestionDao;
import ru.otus.spring01.questionnaire.domain.Question;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    @Override
    public void startQuestions() {

        ConsoleService cs = new ConsoleService();
        int correctAnswersCounter = 0;
        int totalQuestions = questionDao.getQuestionList().size();


        if (totalQuestions != 0) {
            cs.printMsg("Введите фамилию: ");
            final String lastName = cs.readMsg();
            cs.printMsg("Введите имя: ");
            final String firstName = cs.readMsg();
            cs.printMsg("Здравствуйте " + firstName + " " + lastName);

            cs.printMsg("Ответьте пожалуйста на " + totalQuestions + " вопросов теста");

            for (Question question : questionDao.getQuestionList()) {
                cs.printMsg("----------------------------------------------");
                cs.printMsg(question.getQuestion());
                cs.printMsg("Введите ответ на вопрос: ");
                String answer = cs.readMsg().trim();
                if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                    cs.printMsg("Ответ правильный");
                    correctAnswersCounter++;
                } else {
                    cs.printMsg("Неправильно");
                }

            }
            cs.printMsg("Вы ответили верно на " + correctAnswersCounter + " вопросов");
        }

        if (totalQuestions == 0) {
            cs.printMsg("Ошибка чтения файла вопросов");
        }
    }

}
