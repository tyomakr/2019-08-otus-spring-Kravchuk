package ru.otus.spring.spring04.questionnaire.springshell.service;

import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;

public class ChangeLocaleService {

    private Locale locale = LocaleContextHolder.getLocale();

    public void changeLocale() {

        if (locale.getCountry().equalsIgnoreCase("RU")) {
            locale = new Locale("en", "EN");
            LocaleContextHolder.setLocale(locale);
        }
        else if (locale.getCountry().equalsIgnoreCase("EN")) {
            locale = new Locale("ru", "RU");
            LocaleContextHolder.setLocale(locale);
        }

    }

}
