package ru.otus.spring.library.repository;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.library.model.Book;
import ru.otus.spring.library.model.Comment;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("ru.otus.spring.library.repository.CommentRepository, ru.otus.spring.library.repository.CommentRepository, ru.otus.spring.library.config")
@DisplayName("CommentRepository должен")
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookRepository bookRepository;

    private static final String TEST_BOOK_TITLE = "Властелин колец";

    @Test
    @DisplayName("удалять все комментарии из базы по ID книги")
    void removeAllCommentsFromBookByBookId() {

        Optional<Book> testBook = bookRepository.findByTitle(TEST_BOOK_TITLE);
        List<Comment> commentList = commentRepository.findAllByBook_Id(testBook.get().getId());

        int beforeRemovingListSize = commentList.size();

        commentRepository.removeAllCommentsFromBookByBookId(testBook.get().getId());
        commentList = commentRepository.findAllByBook_Id(testBook.get().getId());
        int afterRemovingListSize = commentList.size();

        Assert.assertNotEquals(beforeRemovingListSize, afterRemovingListSize);
        Assert.assertEquals(afterRemovingListSize, 0);
    }
}