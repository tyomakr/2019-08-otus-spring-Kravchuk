package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.library.webmvc.service.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService gs;
    private final MessageSource ms;

    @GetMapping("/genres")
    public String getGenresListPage(Model model) {
        model.addAttribute("genreList", gs.findAll());
        model.addAttribute("view", "pages/genres");
        model.addAttribute("pageTitle", ms.getMessage("w.page.g.title", null, LocaleContextHolder.getLocale()));
        return "base-layout";
    }
}
