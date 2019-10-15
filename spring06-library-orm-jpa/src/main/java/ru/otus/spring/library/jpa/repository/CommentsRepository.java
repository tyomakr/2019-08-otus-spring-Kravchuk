package ru.otus.spring.library.jpa.repository;

import ru.otus.spring.library.jpa.domain.Comment;

public interface CommentsRepository {

    void findAllComments();

    void findCommentById(long id);

    void saveComment(Comment c, long bookId);

    void deleteComment(long id);
}
