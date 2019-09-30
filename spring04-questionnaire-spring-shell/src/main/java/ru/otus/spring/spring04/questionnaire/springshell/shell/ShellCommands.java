package ru.otus.spring.spring04.questionnaire.springshell.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.spring04.questionnaire.springshell.domain.User;
import ru.otus.spring.spring04.questionnaire.springshell.service.ChangeLocaleService;
import ru.otus.spring.spring04.questionnaire.springshell.service.QuestionService;
import ru.otus.spring.spring04.questionnaire.springshell.service.UserService;

import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final UserService userService;
    private final QuestionService questionService;
    private final ChangeLocaleService changeLocaleService;
    private final MessageSource ms;

    private User user;

    @ShellMethod(value = "Input username", key = {"1", "l", "username"})
    public void inputUsername() {
        user = userService.createUser();
    }

    @ShellMethod(value = "Change language", key = {"9", "lang", "change-language"})
    public void changeLang() {
        changeLocaleService.changeLocale();
    }

    @ShellMethod(value = "Start answering questions", key = {"2", "start", "s", "questions"})
    @ShellMethodAvailability(value = "isUserSetName")
    public void startQuestions() {
		questionService.startQuestions();
    }

    @ShellMethod(value = "Get results", key = {"3", "result", "r"})
    @ShellMethodAvailability(value = "isUserFinishTest")
    public String getTestResults() {
        Locale lc = LocaleContextHolder.getLocale();
        return ms.getMessage("qs.start.answer.total", new String[]{String.valueOf(user.getGrade())}, lc);
    }

    private Availability isUserSetName() {
        Locale lc = LocaleContextHolder.getLocale();
        return user == null ? Availability.unavailable(ms.getMessage("cpp.er.intr", null, lc)): Availability.available();
    }

    private Availability isUserFinishTest() {
        Locale lc = LocaleContextHolder.getLocale();
        if (isUserSetName().isAvailable()) {
            if (user.isTestCompleted()) {
                return Availability.available();
            }
        }
        return Availability.unavailable(ms.getMessage("cpp.er.not.graded", null, lc));
    }
}
