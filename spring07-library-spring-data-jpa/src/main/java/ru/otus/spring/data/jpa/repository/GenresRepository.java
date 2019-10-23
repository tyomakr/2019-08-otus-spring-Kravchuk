package ru.otus.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.jpa.domain.Genre;

import java.util.Optional;

public interface GenresRepository extends JpaRepository<Genre, Long> {

    Optional <Genre> findGenreByGenreName(String genreName);
}
