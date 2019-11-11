package ru.otus.spring.library.webmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.service.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bs;
    private final AuthorService as;
    private final GenreService gs;
    private final CommentService cs;
    private final MsgService msgService;


    @GetMapping("/books")
    public String getBooksListPage(Model model) {
        model.addAttribute("bookList", bs.findAll());
        model.addAttribute("pageTitle", msgService.getMsg("bl.page.header"));
        return "pages/books";
    }

    @PostMapping(value = "/books/create")
    public String createBook(@RequestParam("title") String bookTitle,
                             @RequestParam("author") String author,
                             @RequestParam("genre") String genre) {
        bs.insertBook(bookTitle, author, genre);
        return "redirect:/books";
    }


    @GetMapping("/books/edit")
    public String getEditBookPage(@RequestParam("id") String bookId, Model model) {
        Optional<Book> optionalBook = bs.findById(bookId);
        optionalBook.ifPresent(book -> {
            model.addAttribute("pageTitle", msgService.getMsg("eb.page.header"));
            model.addAttribute("availableAuthorsList", as.findAll().stream().filter(author -> !book.getAuthors().contains(author)).collect(Collectors.toList()));
            model.addAttribute("availableGenresList", gs.findAll().stream().filter(genre -> !book.getGenres().contains(genre)).collect(Collectors.toList()));
            model.addAttribute("bookCommentsList", cs.findAllCommentsByBookId(bookId));
            model.addAttribute(book);
        });
        return "pages/edit-book";
    }


    @PostMapping("/books/edit/title")
    public String updateTitle(@ModelAttribute("bookFromForm") Book bookFromForm) {
        Optional<Book> bookOptional = bs.findById(bookFromForm.getId());
        bookOptional.ifPresent(book -> {
            book.setTitle(bookFromForm.getTitle());
            bs.updateBookTitle(book);
        });
        return "redirect:/books/edit?id=" + bookFromForm.getId();
    }


    @PostMapping("/books/edit/{id}/author/delete/{authorId}")
    public String removeBookAuthor(@PathVariable("id") String bookId, @PathVariable("authorId") String authorId) {
        bs.removeBookAuthor(bookId, authorId);
        return "redirect:/books/edit?id=" + bookId;
    }

    @PostMapping("/books/edit/{id}/genre/delete/{genreId}")
    public String removeBookGenre(@PathVariable("id") String bookId, @PathVariable("genreId") String genreId) {
        bs.removeBookGenre(bookId, genreId);
        return "redirect:/books/edit?id=" + bookId;
    }

    @PostMapping("/books/edit/{id}/author/add")
    public String addBookAuthor(@PathVariable("id") String bookId, @RequestParam("authorId") String authorId) {
        if (!authorId.equals(msgService.getMsg("eb.av.authors.choose"))) {
            bs.addBookAuthor(bookId, authorId);
        }
        return "redirect:/books/edit?id=" + bookId;
    }

    @PostMapping("/books/edit/{id}/genre/add")
    public String addBookGenre(@PathVariable("id") String bookId, @RequestParam("genreId") String genreId) {
        if (!genreId.equals(msgService.getMsg("eb.av.genres.choose"))) {
            bs.addBookGenre(bookId, genreId);
        }
        return "redirect:/books/edit?id=" + bookId;
    }

    @PostMapping("/books/remove/{id}")
    public String removeBook(@PathVariable("id") String bookId) {
        bs.removeBook(bookId);
        return "redirect:/books";
    }
}
