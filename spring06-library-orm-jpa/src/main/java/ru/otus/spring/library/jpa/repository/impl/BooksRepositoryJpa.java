package ru.otus.spring.library.jpa.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Book;
import ru.otus.spring.library.jpa.repository.BooksRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional
public class BooksRepositoryJpa implements BooksRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Book> findAllBooks() {
        return em.createQuery("SELECT b FROM Book b").getResultList();
    }


    @Override
    public Book findBookById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getBookId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public void deleteBook(Book book) {
        em.remove(book);
    }

}
