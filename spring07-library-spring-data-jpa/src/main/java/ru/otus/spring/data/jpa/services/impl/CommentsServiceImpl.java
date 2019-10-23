package ru.otus.spring.data.jpa.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.data.jpa.domain.Book;
import ru.otus.spring.data.jpa.domain.Comment;
import ru.otus.spring.data.jpa.repository.BooksRepository;
import ru.otus.spring.data.jpa.repository.CommentsRepository;
import ru.otus.spring.data.jpa.services.CommentsService;
import ru.otus.spring.data.jpa.services.IOService;

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

        Optional<Book> book = booksRepo.findById(bookId);

        if (book.isPresent()) {
            List<Comment> comments = commentsRepo.findCommentByBook(book.get());
            printCommentsList(comments);
        }
    }

    @Override
    @Transactional
    public void setCommentFromBook(Long bookId, String commentText) {
        Optional<Book> book = booksRepo.findById(bookId);
        book.ifPresent(value -> commentsRepo.save(new Comment(commentText, value)));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentsRepo.findById(commentId);
        comment.ifPresent(commentsRepo::delete);
    }


    private void printCommentsList(List<Comment> commentsList) {
        for (Comment comment : commentsList) {
            ioService.printItemsList("%-4s %-500s %n", comment.getCommentId(), comment.getCommentText());
        }
    }


}
