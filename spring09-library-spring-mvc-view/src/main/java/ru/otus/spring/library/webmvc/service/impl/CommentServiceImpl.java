package ru.otus.spring.library.webmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.library.webmvc.domain.Comment;
import ru.otus.spring.library.webmvc.repository.CommentRepository;
import ru.otus.spring.library.webmvc.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllCommentsByBookId(String bookId) {
        return commentRepository.findAllByBook_Id(bookId);
    }
}
