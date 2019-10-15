package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.jpa.domain.Author;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.domain.Genre;
import ru.otus.spring.library.jpa.repository.BooksRepository;
import ru.otus.spring.library.jpa.services.BooksService;
import ru.otus.spring.library.jpa.services.IOService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository bookRepo;
    private final IOService ioService;

    @Override
    @Transactional
    public void findAll() {
        List<Book> bookList = bookRepo.findAllBooks();
        ioService.printMsg("bs.msg.list");

        String tableFormatter = "%-4s %-50s %-50s %-50s %n";

        ioService.printItemsList(tableFormatter, ioService.getMsg("bs.msg.l.h0"),
                ioService.getMsg("bs.msg.l.h1"), ioService.getMsg("bs.msg.l.h2"),
                ioService.getMsg("bs.msg.l.h3"));
        for (Book book : bookList) {
            ioService.printItemsList(tableFormatter, book.getBookId(), book.getBookName(),
                    book.getAuthorsList().stream().map(Author::getAuthorName).collect(Collectors.toList()),
                    book.getGenresList().stream().map(Genre::getGenreName).collect(Collectors.toList()));
        }
    }
}
