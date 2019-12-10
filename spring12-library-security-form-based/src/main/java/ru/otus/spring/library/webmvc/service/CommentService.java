package ru.otus.spring.library.webmvc.service;

import ru.otus.spring.library.webmvc.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllCommentsByBookId(String bookId);
}
