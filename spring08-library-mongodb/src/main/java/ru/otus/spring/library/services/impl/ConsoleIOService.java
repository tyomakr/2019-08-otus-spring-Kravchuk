package ru.otus.spring.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.services.IOService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ConsoleIOService implements IOService {

    private final MessageSource ms;

    public void printMsg(String s) {
        System.out.println(ms.getMessage(s, null, getLocale()));
    }

    public String getMsg(String s) {
        return ms.getMessage(s, null, getLocale());
    }

    public void printItemsList(String format, Object... args) {
        System.out.printf(format, args);
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}