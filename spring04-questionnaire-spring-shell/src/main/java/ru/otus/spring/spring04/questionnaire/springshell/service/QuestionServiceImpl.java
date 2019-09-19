package ru.otus.spring.spring04.questionnaire.springshell.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.spring04.questionnaire.springshell.dao.QuestionDao;
import ru.otus.spring.spring04.questionnaire.springshell.domain.Question;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final MessageSource ms;
    private final UserService userService;
    private final QuestionDao questionDao;

    private ConsoleService cs = new ConsoleService();

    @Override
    public void startQuestions() {
        Locale lc = LocaleContextHolder.getLocale();

        int totalQuestions = questionDao.getQuestionList().size();

        if (totalQuestions != 0) {
            cs.printMsg(ms.getMessage("qs.start.ans.pls", new String[] {String.valueOf(totalQuestions)}, lc));

            int correctAnswersCounter = 0;

            for (Question question : questionDao.getQuestionList()) {
                cs.printMsg(ms.getMessage("qs.sep", null, lc));
                cs.printMsg(question.getQuestion());
                cs.printMsg(ms.getMessage("qs.start.answer", null, lc));
                String answer = cs.readMsg().trim();
                if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                    cs.printMsg(ms.getMessage("qs.start.answer.ok", null, lc));
                    correctAnswersCounter++;
                } else {
                    cs.printMsg(ms.getMessage("qs.start.answer.no", null, lc));
                }
            }
            cs.printMsg(ms.getMessage("cpp.small.sep", null, lc));
            cs.printMsg(ms.getMessage("qs.finish", null, lc));
            userService.setGrade(correctAnswersCounter);
        }

        if (totalQuestions == 0) {
            cs.printMsg(ms.getMessage("qs.start.err.reading.file", null, lc));
        }
    }
}
