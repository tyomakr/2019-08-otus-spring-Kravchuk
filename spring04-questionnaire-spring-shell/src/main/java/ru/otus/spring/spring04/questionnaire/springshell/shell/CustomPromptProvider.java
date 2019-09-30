package ru.otus.spring.spring04.questionnaire.springshell.shell;

import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;
import ru.otus.spring.spring04.questionnaire.springshell.service.ConsoleService;
import ru.otus.spring.spring04.questionnaire.springshell.service.IOService;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomPromptProvider implements PromptProvider {

    private final MessageSource ms;
    private final IOService ioService;

    @Override
    public AttributedString getPrompt() {
        getMenu();
        String prompt = "questionnaire-console";
        return new AttributedString(prompt + ":>");
    }

    private void getMenu(){
        Locale lc = LocaleContextHolder.getLocale();
        ioService.printMsg("\n" + ms.getMessage("qs.sep", null, lc));
        ioService.printMsg(ms.getMessage("cpp.menu1",null, lc));
        ioService.printMsg(ms.getMessage("cpp.menu2",null, lc));
        ioService.printMsg(ms.getMessage("cpp.menu3",null, lc));
        ioService.printMsg(ms.getMessage("cpp.menu9",null, lc));
        ioService.printMsg(ms.getMessage("cpp.small.sep", null, lc));
    }

}