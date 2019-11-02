package ru.otus.spring.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.model.Book;
import ru.otus.spring.library.model.Comment;
import ru.otus.spring.library.repository.BookRepository;
import ru.otus.spring.library.repository.CommentRepository;
import ru.otus.spring.library.services.CommentService;
import ru.otus.spring.library.services.IOService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final IOService ioService;

    @Override
    public void findAndShowCommentsByBookId(String bookId) {
        List<Comment> commentList = commentRepository.findAllByBook_Id(bookId);

        String tableFormatter = "%-26s %-150s %n";
        if (commentList.size() > 0) {
            ioService.printItemsList(tableFormatter, ioService.getMsg("cs.head.c.id"), ioService.getMsg("cs.head.c.text"));
            for(Comment comment : commentList) {
                ioService.printItemsList(tableFormatter, comment.getId(), comment.getCommentText());
            }
        } else {
            ioService.printMsg("cs.c.empty");
        }
    }

    @Override
    public void addCommentToBookByBookId(String bookId, String commentText) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(value -> commentRepository.save(new Comment(commentText, value)));
    }

    @Override
    public void deleteCommentById(String commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }
}
