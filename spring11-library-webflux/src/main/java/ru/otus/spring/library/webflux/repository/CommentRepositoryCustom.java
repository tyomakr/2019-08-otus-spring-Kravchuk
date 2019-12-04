package ru.otus.spring.library.webflux.repository;

public interface CommentRepositoryCustom {

    void removeAllCommentsFromBookByBookId(String bookId);
}
