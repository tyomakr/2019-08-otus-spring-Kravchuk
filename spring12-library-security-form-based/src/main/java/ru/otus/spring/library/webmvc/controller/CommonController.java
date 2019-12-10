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
        model.addAttribute("pageTitle", "Spring Online Library");
        return "home/index";
    }

    @GetMapping("/info")
    public String getInfoPage(Model model) {
        model.addAttribute("pageTitle", "Information page");
        return "pages/info";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("pageTitle", "Login page");
        return "pages/login";
    }
}
