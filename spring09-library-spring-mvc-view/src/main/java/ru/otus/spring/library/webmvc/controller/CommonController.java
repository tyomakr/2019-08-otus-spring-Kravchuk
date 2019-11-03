package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("view", "home/index");
        model.addAttribute("pageTitle", "Spring Online Library");
        return "base-layout";
    }
}
