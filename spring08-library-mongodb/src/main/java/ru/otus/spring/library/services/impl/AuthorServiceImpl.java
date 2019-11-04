package ru.otus.spring.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.model.Author;
import ru.otus.spring.library.repository.AuthorRepository;
import ru.otus.spring.library.services.AuthorService;
import ru.otus.spring.library.services.IOService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final IOService ioService;


    @Override
    public void findAll() {
        List<Author> authorList = authorRepository.findAll();
        String tableFormatter = "%-26s %-60s %n";
        ioService.printItemsList(tableFormatter, ioService.getMsg("as.head.a.id"), ioService.getMsg("bs.head.b.author"));

        for(Author author : authorList) {
            ioService.printItemsList(tableFormatter, author.getId(), author.getAuthorName());
        }
    }

    @Override
    public void insertAuthor(String authorName) {
        if (!authorRepository.existsByAuthorNameEqualsIgnoreCase(authorName)) {
            Author author = new Author(authorName);
            authorRepository.save(author);
        }
        else {
            ioService.printMsg("as.err.a.exists");
        }
    }

    @Override
    public void updateAuthor(String oldAuthorName, String newAuthorName) {
        Optional<Author> optionalAuthor = authorRepository.findAuthorByAuthorName(oldAuthorName);
        ioService.printMsg(optionalAuthor.map(author ->{
            author.setAuthorName(newAuthorName);
            authorRepository.save(author);
            return "as.a.updated";
        }).orElse("as.err.a.not.exists"));
    }
}
