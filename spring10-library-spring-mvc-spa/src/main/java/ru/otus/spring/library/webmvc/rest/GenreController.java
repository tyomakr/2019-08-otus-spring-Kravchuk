package ru.otus.spring.library.webmvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.library.webmvc.repository.GenreRepository;
import ru.otus.spring.library.webmvc.rest.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/genres")
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreDto::toDto)
                .collect(Collectors.toList());
    }
}
