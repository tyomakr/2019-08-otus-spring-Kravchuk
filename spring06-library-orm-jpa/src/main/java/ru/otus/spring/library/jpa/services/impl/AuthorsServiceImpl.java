package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.jpa.domain.Author;
import ru.otus.spring.library.jpa.repository.AuthorsRepository;
import ru.otus.spring.library.jpa.services.AuthorsService;
import ru.otus.spring.library.jpa.services.IOService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {

    private final AuthorsRepository authorsRepo;
    private final IOService ioService;

    @Override
    public void findAllAuthors() {
        List<Author> authorList = authorsRepo.findAllAuthors();
        ioService.printMsg("as.msg.list");
        for (Author author : authorList) {
            ioService.printItemsList("%-4s %-50s %n", author.getAuthorId(), author.getAuthorName());
        }
    }


    @Override
    public void insertAuthor(String authorName) {
        authorsRepo.saveAuthor(new Author(authorName));
    }

    @Override
    public void updateAuthorName(String originalAuthorName, String changedAuthorName) {
        try {
            Author author = getAuthorByName(originalAuthorName);
            author.setAuthorName(changedAuthorName);
            authorsRepo.saveAuthor(author);
        } catch (Exception e) {
            ioService.printMsg("as.err.not.exists");
        }
    }

    public Author getAuthorByName(String authorName) {
        Optional<Author> optAuthor = authorsRepo.findAuthorByName(authorName);
        return optAuthor.orElseGet(() -> authorsRepo.saveAuthor(new Author(authorName)));
    }
}
