package ru.otus.spring.spring04.questionnaire.springshell.service;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@DisplayName("Класс ChangeLocaleService")
class ChangeLocaleServiceTest {

    private Locale expectedLocale = new Locale("ru", "RU");

    @Test
    @DisplayName("корректно переключает локаль")
    void changeRuLocaleToEng() {
        LocaleContextHolder.setLocale(expectedLocale);

        ChangeLocaleService cls = new ChangeLocaleService();
        cls.changeLocale();
        expectedLocale = LocaleContextHolder.getLocale();
        Locale actualLocale = new Locale("en", "EN");

        Assert.assertEquals(expectedLocale, actualLocale);





    }
}