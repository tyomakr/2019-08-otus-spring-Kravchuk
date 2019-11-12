package ru.otus.spring.library.webmvc.repository;

public interface CommentRepositoryCustom {

    void removeAllCommentsFromBookByBookId(String bookId);
}
