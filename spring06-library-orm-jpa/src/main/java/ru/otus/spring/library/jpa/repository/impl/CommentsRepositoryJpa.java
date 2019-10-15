package ru.otus.spring.library.jpa.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Comment;
import ru.otus.spring.library.jpa.repository.CommentsRepository;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional
public class CommentsRepositoryJpa implements CommentsRepository {
    @Override
    public void findAllComments() {

    }

    @Override
    public void findCommentById(long id) {

    }

    @Override
    public void saveComment(Comment c, long bookId) {

    }

    @Override
    public void deleteComment(long id) {

    }
}
