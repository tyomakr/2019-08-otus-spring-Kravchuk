package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Author;
import ru.otus.spring.library.webmvc.repository.AuthorRepository;
import ru.otus.spring.library.webmvc.service.AuthorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Override
    public Author findOrCreateAuthor(String bookAuthor) {
        Optional<Author> authorOptional = authorRepository.findAuthorByAuthorName(bookAuthor);
        return authorRepository.save(authorOptional.orElseGet(() -> new Author(bookAuthor)));
    }
}


