package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.jpa.domain.Author;
import ru.otus.spring.library.jpa.repository.AuthorsRepository;
import ru.otus.spring.library.jpa.services.AuthorsService;
import ru.otus.spring.library.jpa.services.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {

    private final AuthorsRepository authorsRepo;
    private final IOService ioService;

    @Override
    public void findAll() {
        List<Author> authorList = authorsRepo.findAllAuthors();
        ioService.printMsg("as.msg.list");
        for (Author author : authorList) {
            ioService.printItemsList("%-4s %-50s %n", author.getAuthorId(), author.getAuthorName());
        }
    }


    @Override
    public void insertAuthor(String authorName) {
        authorsRepo.saveAuthor(new Author(authorName));
//        if (!authorsRepo.isExists(authorName)) {
//            authorsRepo.saveAuthor(new Author(authorName));
//        } else {
//            ioService.printMsg("as.err.exists");
//        }
    }

    @Override
    public void updateAuthorName(String originalAuthorName, String changedAuthorName) {
        try {
            Author author = authorsRepo.findAuthorByName(originalAuthorName);
            author.setAuthorName(changedAuthorName);
            authorsRepo.saveAuthor(author);
        } catch (Exception e) {
            ioService.printMsg("as.err.not.exists");
        }

//        if (authorsRepo.isExists(originalAuthorName)) {
//            Author author = authorsRepo.findAuthorByName(originalAuthorName);
//            author.setAuthorName(changedAuthorName);
//            authorsRepo.saveAuthor(author);
//        } else {
//            ioService.printMsg("as.err.not.exists");
//        }
    }
}
