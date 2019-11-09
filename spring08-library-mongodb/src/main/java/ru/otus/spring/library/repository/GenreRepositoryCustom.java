package ru.otus.spring.library.repository;

import com.mongodb.client.result.UpdateResult;

public interface GenreRepositoryCustom {

    UpdateResult updateAllGenresInBooks(String oldGenreTitle, String newGenreTitle);
}
