package ru.otus.spring.library.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.library.webflux.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String>, CommentRepositoryCustom {

    Flux<Comment> findAllByBook_Id(String bookId);
}