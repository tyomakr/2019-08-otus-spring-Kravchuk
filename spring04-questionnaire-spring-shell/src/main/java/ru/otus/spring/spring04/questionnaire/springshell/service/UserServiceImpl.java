package ru.otus.spring.spring04.questionnaire.springshell.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.spring04.questionnaire.springshell.domain.User;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MessageSource ms;
    private final IOService ioService;
    private User user;

    @Override
    public User createUser() {
        Locale lc = LocaleContextHolder.getLocale();
        user = new User();

        ioService.printMsg(ms.getMessage("qs.start.firstname", null, lc));
        String firstName = ioService.readMsg();
        if (firstName.isEmpty()) {
            firstName = "Student";
        }
        user.setFirstName(firstName);

        ioService.printMsg(ms.getMessage("qs.start.lastname", null, lc));
        String lastName = ioService.readMsg();
        if (lastName.isEmpty()) {
            lastName = "Anonymous";
        }
        user.setLastName(lastName);

        ioService.printMsg(ms.getMessage("qs.start.hello", new String[]{String.valueOf(user.getUserName())}, lc));
        return user;
    }

    @Override
    public void setGrade(int grade) {
        user.setTestCompleted(true);
        user.setGrade(grade);
    }
}
