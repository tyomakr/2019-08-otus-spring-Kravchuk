package ru.otus.spring.library.services;

public interface CommentService {

    void findAndShowCommentsByBookId(String bookId);

    void addCommentToBookByBookId(String bookId, String commentText);

    void deleteCommentById(String commentId);
}
