package ru.otus.spring.library.webmvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.webmvc.mapper.BookMapper;
import ru.otus.spring.library.webmvc.rest.dto.BookDto;
import ru.otus.spring.library.webmvc.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.domainToDto(bookService.findAll()));
    }


    @PostMapping(value = "/books/create")
    public void createBook(@RequestBody BookDto bookDto) {
        bookService.insertBook(bookDto.getTitle(), bookDto.getAuthors(), bookDto.getGenres());
    }

}
