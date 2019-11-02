package ru.otus.spring.library.repository;

public interface CommentRepositoryCustom {

    void removeAllCommentsFromBookByBookId(String bookId);
}
