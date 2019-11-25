package ru.otus.spring.library.webmvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.webmvc.domain.Book;
import ru.otus.spring.library.webmvc.domain.Comment;
import ru.otus.spring.library.webmvc.mapper.BookMapper;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;
import ru.otus.spring.library.webmvc.rest.resp.ApiResponse;
import ru.otus.spring.library.webmvc.service.AuthorService;
import ru.otus.spring.library.webmvc.service.BookService;
import ru.otus.spring.library.webmvc.service.CommentService;
import ru.otus.spring.library.webmvc.service.GenreService;

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


    @PostMapping(value = "/books/create")
    public ApiResponse<Book> createBook(@RequestBody BookDto bookDto) {
        return new ApiResponse<>(HttpStatus.OK.value(),
                bookService.insertBook(bookDto.getTitle(), bookDto.getAuthors(), bookDto.getGenres()));
    }


    @GetMapping("/books/{id}")
    public ApiResponse<Book> getBookById(@PathVariable("id") String id) {
        Optional<Book> optionalBook = bookService.findById(id);

        return new ApiResponse<>(HttpStatus.OK.value(),
                optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }


    @PutMapping("/books/update/")
    public ApiResponse<Book> updateBook(@RequestBody Book book) {
        return new ApiResponse<>(HttpStatus.OK.value(), bookService.updateBook(book));
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Void> deleteBook(@PathVariable("id") String id) {
        bookService.removeBook(id);
        return new ApiResponse<>(HttpStatus.OK.value(),null);
    }


    @GetMapping("/books/{id}/comments/")
    public ApiResponse<List<Comment>> getBookCommentsByBookId(@PathVariable("id") String id) {
        Optional<Book> optionalBook = bookService.findById(id);
        return optionalBook.<ApiResponse<List<Comment>>>map(book ->
                new ApiResponse<>(HttpStatus.OK.value(), commentService.findAllCommentsByBookId(book.getId())))
                .orElseGet(() -> new ApiResponse<>(HttpStatus.NO_CONTENT.value(), null));
    }
}
