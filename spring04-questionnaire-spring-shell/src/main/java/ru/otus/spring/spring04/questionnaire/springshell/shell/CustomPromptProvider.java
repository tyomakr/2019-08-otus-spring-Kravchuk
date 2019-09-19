package ru.otus.spring.spring04.questionnaire.springshell.shell;

import org.jline.utils.AttributedString;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;
import ru.otus.spring.spring04.questionnaire.springshell.service.ConsoleService;

import java.util.Locale;

@Component
public class CustomPromptProvider implements PromptProvider {

    private final MessageSource ms;
    private ConsoleService cs = new ConsoleService();

    public CustomPromptProvider(MessageSource ms) {
        this.ms = ms;
    }

    @Override
    public AttributedString getPrompt() {
        getMenu();
        String prompt = "questionnaire-console";
        return new AttributedString(prompt + ":>");
    }

    private void getMenu(){
        Locale lc = LocaleContextHolder.getLocale();
        cs.printMsg("\n" + ms.getMessage("qs.sep", null, lc));
        cs.printMsg(ms.getMessage("cpp.menu1",null, lc));
        cs.printMsg(ms.getMessage("cpp.menu2",null, lc));
        cs.printMsg(ms.getMessage("cpp.menu3",null, lc));
        cs.printMsg(ms.getMessage("cpp.menu9",null, lc));
        cs.printMsg(ms.getMessage("cpp.small.sep", null, lc));
    }

}