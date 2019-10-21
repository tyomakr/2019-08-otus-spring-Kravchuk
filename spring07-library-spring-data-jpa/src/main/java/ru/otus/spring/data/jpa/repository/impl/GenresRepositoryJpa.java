package ru.otus.spring.data.jpa.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.data.jpa.domain.Genre;
import ru.otus.spring.data.jpa.repository.GenresRepository;

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
public class GenresRepositoryJpa implements GenresRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Genre> findAllGenres() {
        return em.createQuery("SELECT g FROM Genre g").getResultList();
    }

    @Override
    public Genre findGenreById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public Optional<Genre> findGenreByName(String name) {
        Objects.requireNonNull(name);
        TypedQuery<Genre> genreTypedQuery = em.createQuery(
                "SELECT g FROM Genre g WHERE LOWER (g.genreName) =: name", Genre.class)
                .setParameter("name", name.toLowerCase());

        try {
            Genre genre = genreTypedQuery.getSingleResult();
            return Optional.of(genre);

        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Genre saveGenre (Genre genre) {
        if (genre.getGenreId() <= 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        return genre;
    }
}