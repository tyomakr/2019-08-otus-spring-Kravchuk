package ru.otus.spring.data.jpa.services;

public interface CommentsService {

    void findCommentsFromBook(Long bookId);

    void setCommentFromBook(Long bookId, String commentText);

    void deleteComment(Long commentId);

}
