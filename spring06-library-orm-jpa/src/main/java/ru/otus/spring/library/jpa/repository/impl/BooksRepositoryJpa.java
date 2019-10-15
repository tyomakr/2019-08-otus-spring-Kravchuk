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

}
