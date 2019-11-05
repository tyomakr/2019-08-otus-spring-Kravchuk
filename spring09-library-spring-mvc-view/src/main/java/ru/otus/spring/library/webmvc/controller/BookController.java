package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.GenreService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bs;
    private final AuthorService as;
    private final GenreService gs;
    private final MessageSource ms;


    @GetMapping("/books")
    public String getBooksListPage(Model model) {
        model.addAttribute("bookList", bs.findAll());
        model.addAttribute("view", "pages/books");
        model.addAttribute("pageTitle", ms.getMessage("w.page.b.title", null, LocaleContextHolder.getLocale()));
        return "base-layout";
    }

    @PostMapping(value = "/books/create")
    public String createBook(@RequestParam("title") String bookTitle,
                             @RequestParam("author") String author,
                             @RequestParam("genre") String genre) {
        bs.insertBook(bookTitle, author, genre);
        return "redirect:/books";
    }

    @GetMapping("/books/editbook")
    public String editBook(@RequestParam("id") String bookId, Model model) {
        Book book = bs.findById(bookId);
        model.addAttribute("view", "pages/editbook");
        model.addAttribute(book);
        return "base-layout";
    }

    @PostMapping("/books/updatebook")
    public String update(@ModelAttribute("bookForm")Book bookForm, Model model) {
        Optional<Book> bookOptional = Optional.ofNullable(bs.findById(bookForm.getId()));
        bookOptional.ifPresent(b -> {
            b.setTitle(bookForm.getTitle());
            b.setAuthors(bookForm.getAuthors());
            b.setGenres(bookForm.getGenres());
            model.addAttribute("book", b);
            bs.updateBook(b);
        });

        return "redirect:/books";
    }

}
