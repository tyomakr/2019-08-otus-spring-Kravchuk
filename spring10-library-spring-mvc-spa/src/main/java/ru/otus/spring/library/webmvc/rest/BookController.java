package ru.otus.spring.library.webmvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Comment;
import ru.otus.spring.library.webmvc.mapper.BookMapper;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;
    private final BookMapper bookMapper;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.domainToDto(bookService.findAll()));
    }


    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.insertBook(bookDto.getTitle(), bookDto.getAuthors(), bookDto.getGenres()));
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
        Optional<Book> optionalBook = bookService.findById(id);
        return optionalBook.map(ResponseEntity::ok).get();
    }


    @PutMapping("/books/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") String id) {
        bookService.removeBook(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/books/{id}/comments/")
    public ResponseEntity<List<Comment>> getBookCommentsByBookId(@PathVariable("id") String id) {
        Optional<Book> optionalBook = bookService.findById(id);
        return optionalBook.map(book -> ResponseEntity.ok(commentService.findAllCommentsByBookId(book.getId()))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
