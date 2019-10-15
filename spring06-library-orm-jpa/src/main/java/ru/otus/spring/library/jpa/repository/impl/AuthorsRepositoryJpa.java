package ru.otus.spring.library.jpa.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.library.jpa.domain.Author;
import ru.otus.spring.library.jpa.repository.AuthorsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
    public Author findAuthorByName(String name) {
        return (Author) em.createQuery("SELECT a FROM Author a WHERE a.authorName LIKE :name")
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public void saveAuthor(Author author) {
        if (author.getAuthorId() <= 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

//    @Override
//    public void updateAuthor(Author author) {
//        Query query = em.createQuery("UPDATE Author a SET authorName = :name WHERE a.authorId = :id")
//                .setParameter("name", author.getAuthorName())
//                .setParameter("id", author.getAuthorId());
//        query.executeUpdate();
//    }

//    @Override
//    public boolean isExists(String name) {
//        Query query = em.createQuery("SELECT 1 FROM Author a WHERE a.authorName LIKE :name")
//                .setParameter("name", name);
//        return query.getResultList().size() > 0;
//    }
}
