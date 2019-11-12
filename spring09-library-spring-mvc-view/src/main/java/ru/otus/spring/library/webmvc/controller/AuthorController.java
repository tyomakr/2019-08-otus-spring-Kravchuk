package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.MsgService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService as;
    private final MsgService msgService;


    @GetMapping("/authors")
    public String getAuthorsListPage(Model model) {
        model.addAttribute("authorList", as.findAll());
        model.addAttribute("pageTitle", msgService.getMsg("al.page.header"));
        return "pages/authors";
    }
}
