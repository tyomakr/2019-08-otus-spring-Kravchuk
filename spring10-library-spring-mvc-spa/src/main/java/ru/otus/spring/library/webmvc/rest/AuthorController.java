package ru.otus.spring.library.webmvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.library.webmvc.repository.AuthorRepository;
import ru.otus.spring.library.webmvc.rest.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorRepository.findAll().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList()));
    }
}
