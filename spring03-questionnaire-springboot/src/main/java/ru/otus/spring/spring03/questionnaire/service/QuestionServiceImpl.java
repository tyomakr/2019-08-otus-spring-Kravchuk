package ru.otus.spring.spring03.questionnaire.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.spring03.questionnaire.dao.QuestionDao;
import ru.otus.spring.spring03.questionnaire.domain.Question;

import java.util.List;
import java.util.Locale;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final MessageSource ms;
    private final AvailableLocalesScannerService alss;
    private QuestionDao questionDao;

    private ConsoleService cs = new ConsoleService();

    public QuestionServiceImpl(QuestionDao questionDao, MessageSource ms, AvailableLocalesScannerService alss) {
        this.questionDao = questionDao;
        this.ms = ms;
        this.alss = alss;
    }

    @Override
    public void startQuestions() {

        Locale locale = selectLocale();
        int totalQuestions = questionDao.getQuestionList(locale).size();

        if (totalQuestions != 0) {
            cs.printMsg(ms.getMessage("qs.sep", null, locale));
            cs.printMsg(ms.getMessage("qs.start.lastname", null, locale));
            final String lastName = cs.readMsg();

            cs.printMsg(ms.getMessage("qs.start.firstname", null, locale));
            final String firstName = cs.readMsg();

            cs.printMsg(ms.getMessage("qs.start.hello", new String[]{firstName + " " + lastName}, locale));
            cs.printMsg(ms.getMessage("qs.start.ans.pls", new String[]{String.valueOf(totalQuestions)}, locale));

            int correctAnswersCounter = 0;

            for (Question question : questionDao.getQuestionList(locale)) {
                cs.printMsg(ms.getMessage("qs.sep", null, locale));
                cs.printMsg(question.getQuestion());
                cs.printMsg(ms.getMessage("qs.start.answer", null, locale));
                String answer = cs.readMsg().trim();

                if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                    cs.printMsg(ms.getMessage("qs.start.answer.ok", null, locale));
                    correctAnswersCounter++;
                } else {
                    cs.printMsg(ms.getMessage("qs.start.answer.no", null, locale));
                }

            }
            cs.printMsg(ms.getMessage("qs.sep", null, locale));
            cs.printMsg(ms.getMessage("qs.start.answer.total", new String[]{String.valueOf(correctAnswersCounter)}, locale));
        }

        if (totalQuestions == 0) {
            cs.printMsg(ms.getMessage("qs.start.err.reading.file", null, locale));
        }
    }

    private Locale selectLocale() {

        Locale selectedLocale = new Locale("ru", "RU");

        List<String> avLocales = alss.getAvailableLocalesList();
        cs.printMsg(ms.getMessage("qs.sep", null, selectedLocale));
        cs.printMsg(ms.getMessage("qs.select.lang", null, selectedLocale));

        for (int i = 1; i <= avLocales.size(); i++) {
            cs.printMsg(i + " " + avLocales.get(i - 1));
        }

        boolean isNotSelectedLocale = true;
        while (isNotSelectedLocale) {

            cs.printMsg(ms.getMessage("qs.select.enter.num", null, selectedLocale));
            int number = Integer.parseInt(cs.readMsg());

            if (number <= avLocales.size()) {
                String sl = avLocales.get(number - 1);
                String lang = sl.substring(0,2);
                int endInd = sl.length();
                String country = sl.substring(3, endInd);
                selectedLocale = new Locale(lang, country);
                isNotSelectedLocale = false;
            }
        }
        return selectedLocale;
    }
}


