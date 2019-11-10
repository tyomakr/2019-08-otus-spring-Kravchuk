package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.service.MsgService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MsgServiceImpl implements MsgService {

    private final MessageSource ms;

    @Override
    public String getMsg(String msg) {
        return ms.getMessage(msg, null, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
