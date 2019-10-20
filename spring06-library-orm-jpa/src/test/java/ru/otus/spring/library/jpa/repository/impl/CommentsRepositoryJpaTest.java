package ru.otus.spring.library.jpa.repository.impl;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.domain.Comment;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CommentsRepositoryJpa.class, BooksRepositoryJpa.class})
@DisplayName("Репозиторий для работы с комментариями")
class CommentsRepositoryJpaTest {

    private static final Long BOOK_ID_3 = 3L;
    private static final int COMMENTS_QTY = 5;
    private static final Long COMMENT_7_ID = 7L;
    private static final Long COMMENT_11_ID = 11L;
    private static final String COMMENT_7_TEXT = "sample seven";

    @Autowired
    CommentsRepositoryJpa comRepo;

    @Autowired
    BooksRepositoryJpa bookRepo;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("находит все комментарии в указанной книге")
    void shouldFindCommentsFromBook() {
        Book testBook = bookRepo.findBookById(BOOK_ID_3);
        val comments = comRepo.findCommentsFromBook(testBook);
        Assert.assertEquals(comments.size(), COMMENTS_QTY);
    }

    @Test
    @DisplayName("находит комментарий по ID")
    void shouldFindCommentById() {
        val comment = comRepo.findCommentById(COMMENT_7_ID);
        Assert.assertEquals(comment.getCommentText(), COMMENT_7_TEXT);
    }

    @Test
    @DisplayName("сохраняет комментарий в БД")
    void shouldSaveComment() {
        val book = bookRepo.findBookById(3);
        Comment eComment = new Comment("TEST TEST TEST", book);
        comRepo.saveComment(eComment);
        Comment aComment = comRepo.findCommentById(COMMENT_11_ID);
        Assert.assertEquals(eComment, aComment);
    }

    @Test
    @DisplayName("удаляет комментарий из БД")
    void shouldDeleteComment() {
        val com = em.find(Comment.class, COMMENT_7_ID);
        comRepo.deleteComment(com);
        Assert.assertNull(comRepo.findCommentById(COMMENT_7_ID));
    }
}