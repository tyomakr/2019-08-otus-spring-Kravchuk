package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.library.webmvc.service.GenreService;
import ru.otus.spring.library.webmvc.service.MsgService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService gs;
    private final MsgService msgService;

    @GetMapping("/genres")
    public String getGenresListPage(Model model) {
        model.addAttribute("genreList", gs.findAll());
        model.addAttribute("pageTitle", msgService.getMsg("gl.page.header"));
        return "pages/genres";
    }
}
