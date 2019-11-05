package ru.otus.spring.library.webmvc.repository;

import com.mongodb.client.result.UpdateResult;

public interface GenreRepositoryCustom {

    UpdateResult updateAllGenresInBooks(String oldGenreTitle, String newGenreTitle);
}
