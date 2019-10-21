package ru.otus.spring.data.jpa.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.data.jpa.domain.Author;
import ru.otus.spring.data.jpa.repository.AuthorsRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
@Transactional
public class AuthorsRepositoryJpa implements AuthorsRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Author> findAllAuthors() {
        return em.createQuery("SELECT a FROM Author a").getResultList();
    }

    @Override
    public Author findAuthorById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public Optional<Author> findAuthorByName(String name) {
        Objects.requireNonNull(name);
        TypedQuery<Author> authorTypedQuery = em.createQuery(
                "SELECT a FROM Author a WHERE LOWER (a.authorName) =: name", Author.class)
                .setParameter("name", name.toLowerCase());

        try {
            Author author = authorTypedQuery.getSingleResult();
            return Optional.of(author);

        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author saveAuthor(Author author) {
        if (author.getAuthorId() <= 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return author;
    }
}
