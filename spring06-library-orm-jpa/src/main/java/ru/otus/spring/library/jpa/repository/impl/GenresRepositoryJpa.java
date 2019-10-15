package ru.otus.spring.library.jpa.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Genre;
import ru.otus.spring.library.jpa.repository.GenresRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
    public Genre findGenreByName(String name) {
        return (Genre) em.createQuery("SELECT g FROM Genre g WHERE g.genreName LIKE :name")
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public void insertGenre(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        Query query = em.createQuery("UPDATE Genre g SET genreName = :name WHERE g.genreId = :id")
                .setParameter("name", genre.getGenreName())
                .setParameter("id", genre.getGenreId());
        query.executeUpdate();
    }

    @Override
    public boolean isExists(String name) {
        Query query = em.createQuery("SELECT 1 FROM Genre g WHERE g.genreName LIKE :name")
                .setParameter("name", name);
        return query.getResultList().size() > 0;
    }
}