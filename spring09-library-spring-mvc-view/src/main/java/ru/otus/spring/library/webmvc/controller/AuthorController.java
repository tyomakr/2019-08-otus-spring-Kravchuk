package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.library.webmvc.service.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService as;
    private final MessageSource ms;


    @GetMapping("/authors")
    public String getAuthorsListPage(Model model) {
        model.addAttribute("authorList", as.findAll());
        model.addAttribute("view", "pages/authors");
        model.addAttribute("pageTitle", ms.getMessage("w.page.a.title", null, LocaleContextHolder.getLocale()));
        return "base-layout";
    }
}
