package ru.otus.spring01.questionnaire.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.questionnaire.dao.QuestionDao;
import ru.otus.spring01.questionnaire.domain.Question;

import java.util.Locale;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final MessageSource ms;

    private ConsoleService cs = new ConsoleService();
    private Locale locale = new Locale("ru", "RU");


    public QuestionServiceImpl(QuestionDao questionDao, MessageSource ms) {
        this.questionDao = questionDao;
        this.ms = ms;
    }

    @Override
    public void startQuestions() {

        selectLocale();

        int totalQuestions = questionDao.getQuestionList().size();

        if (totalQuestions != 0) {
            printBundleMsg("qs.sep");
            printBundleMsg("qs.start.lastname");
            final String lastName = cs.readMsg();

            printBundleMsg("qs.start.firstname");
            final String firstName = cs.readMsg();

            cs.printMsg(ms.getMessage("qs.start.hello", new String[] {firstName + " " + lastName}, locale));
            cs.printMsg(ms.getMessage("qs.start.ans.pls", new String[] {String.valueOf(totalQuestions)}, locale));


            int correctAnswersCounter = 0;

            for (Question question : questionDao.getQuestionList()) {
                printBundleMsg("qs.sep");
                cs.printMsg(question.getQuestion());
                printBundleMsg("qs.start.answer");
                String answer = cs.readMsg().trim();
                if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                    printBundleMsg("qs.start.answer.ok");
                    correctAnswersCounter++;
                } else {
                    printBundleMsg("qs.start.answer.no");
                }

            }
            printBundleMsg("qs.sep");
            cs.printMsg(ms.getMessage("qs.start.answer.total", new String[] {String.valueOf(correctAnswersCounter)}, locale));
        }

        if (totalQuestions == 0) {
            printBundleMsg("qs.start.err.reading.file");
        }
    }


    private void selectLocale() {
        boolean isNotSelectedLocale = true;
        while (isNotSelectedLocale) {

            try {
                printBundleMsg("qs.sep");
                printBundleMsg("qs.select.lang");
                printBundleMsg("qs.select.rus");
                printBundleMsg("qs.select.eng");
                printBundleMsg("qs.select.enter.num");
                int number = Integer.parseInt(cs.readMsg());

                switch (number) {

                    case 1:
                        locale = new Locale("ru", "RU");
                        isNotSelectedLocale = false;
                        questionDao.setLocale(locale);
                        printBundleMsg("qs.select.selected.lang");
                        break;

                    case 2:
                        locale = new Locale("en", "EN");
                        isNotSelectedLocale = false;
                        questionDao.setLocale(locale);
                        printBundleMsg("qs.select.selected.lang");
                        break;

                    default:
                        printBundleMsg("qs.select.not.sel.lang");
                }

            } catch (Exception e) {
                printBundleMsg("qs.select.digits.only");
            }
        }
    }

    private void printBundleMsg(String s) {
        cs.printMsg(ms.getMessage(s, null, locale));
    }

}
