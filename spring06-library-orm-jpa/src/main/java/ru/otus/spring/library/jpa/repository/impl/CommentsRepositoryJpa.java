package ru.otus.spring.library.jpa.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.domain.Comment;
import ru.otus.spring.library.jpa.repository.CommentsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional
public class CommentsRepositoryJpa implements CommentsRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> findCommentsFromBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.book =:book", Comment.class)
                .setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public Comment findCommentById(Long id) {
       return em.find(Comment.class, id);
    }

    @Override
    public void saveComment(Comment c) {
        em.persist(c);
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }


}
