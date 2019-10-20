package ru.otus.spring.library.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.domain.Comment;
import ru.otus.spring.library.jpa.repository.BooksRepository;
import ru.otus.spring.library.jpa.repository.CommentsRepository;
import ru.otus.spring.library.jpa.services.CommentsService;
import ru.otus.spring.library.jpa.services.IOService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final BooksRepository booksRepo;
    private final CommentsRepository commentsRepo;
    private final IOService ioService;


    @Override
    @Transactional
    public void findCommentsFromBook(Long bookId) {
        Book book = booksRepo.findBookById(bookId);
        List<Comment> comments = commentsRepo.findCommentsFromBook(book);
        printCommentsList(comments);
    }

    @Override
    @Transactional
    public void setCommentFromBook(Long bookId, String commentText) {
        Optional<Book> book = Optional.ofNullable(booksRepo.findBookById(bookId));
        commentsRepo.saveComment(new Comment(commentText, book.get()));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Optional<Comment> comment = Optional.ofNullable(commentsRepo.findCommentById(commentId));
        comment.ifPresent(commentsRepo::deleteComment);
    }


    private void printCommentsList(List<Comment> commentsList) {
        for (Comment comment : commentsList) {
            ioService.printItemsList("%-4s %-500s %n", comment.getCommentId(), comment.getCommentText());
        }
    }


}
