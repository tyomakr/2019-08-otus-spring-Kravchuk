package ru.otus.spring.library.webmvc.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model, HttpServletResponse response) {
        model.addAttribute("pageTitle", "ERROR PAGE");
        model.addAttribute("errStatus", response.getStatus());

        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }


    @GetMapping("/error-403")
    public String getError403Page(Model model) {
        model.addAttribute("pageTitle", "ACCESS ERROR");
        return "errors/error-403";
    }
}
