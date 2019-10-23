package ru.otus.spring.data.jpa.repository;

import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.data.jpa.domain.Book;
import ru.otus.spring.data.jpa.domain.Comment;

@DataJpaTest
@DisplayName("Репозиторий для работы с комментариями")
class CommentsRepositoryJpaTest {

    private static final Long BOOK_ID_3 = 3L;
    private static final int COMMENTS_QTY = 5;
    private static final Long COMMENT_7_ID = 7L;
    private static final Long COMMENT_11_ID = 11L;
    private static final String COMMENT_7_TEXT = "sample seven";

    @Autowired
    CommentsRepository comRepo;

    @Autowired
    BooksRepository bookRepo;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("находит все комментарии в указанной книге")
    void shouldFindCommentsFromBook() {
        Book testBook = bookRepo.findById(BOOK_ID_3).get();
        val comments = comRepo.findCommentByBook(testBook);
        Assert.assertEquals(comments.size(), COMMENTS_QTY);
    }

    @Test
    @DisplayName("находит комментарий по ID")
    void shouldFindCommentById() {
        val comment = comRepo.findById(COMMENT_7_ID).get();
        Assert.assertEquals(comment.getCommentText(), COMMENT_7_TEXT);
    }

    @Test
    @DisplayName("сохраняет комментарий в БД")
    void shouldSaveComment() {
        val book = bookRepo.findById(3L);
        Comment eComment = new Comment("TEST TEST TEST", book.get());
        comRepo.save(eComment);
        Comment aComment = comRepo.findById(COMMENT_11_ID).get();
        Assert.assertEquals(eComment, aComment);
    }

    @Test
    @DisplayName("удаляет комментарий из БД")
    void shouldDeleteComment() {
        val com = em.find(Comment.class, COMMENT_7_ID);
        comRepo.delete(com);
        Assert.assertNull(em.find(Comment.class, COMMENT_7_ID));
    }
}