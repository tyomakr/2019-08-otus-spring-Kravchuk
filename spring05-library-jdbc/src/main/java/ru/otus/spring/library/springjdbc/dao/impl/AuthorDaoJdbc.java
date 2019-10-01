package ru.otus.spring.library.springjdbc.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.springjdbc.dao.AuthorDao;
import ru.otus.spring.library.springjdbc.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Author getById(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("SELECT * FROM authors WHERE id = :id", params, new AuthorMapper());
    }

    @Override
    public Author getByName(String authorName) {
        Map<String, Object> params = Collections.singletonMap("authorName", authorName);
        return jdbc.queryForObject("SELECT * FROM authors WHERE (authorName) = :authorName", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.getJdbcOperations().query("SELECT * FROM authors", new AuthorMapper());
    }

    @Override
    public void insert(Author author) {
        jdbc.getJdbcOperations().update("INSERT INTO AUTHORS (AUTHORNAME) values ( ? )", author.getAuthorName());
    }

    @Override
    public void update(Author oldAuthor, String newAuthorName) {
        jdbc.getJdbcOperations().update("UPDATE AUTHORS set AUTHORNAME = ? where ID = ?", newAuthorName, oldAuthor.getId());
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String authorName = resultSet.getString("authorName");
            return new Author(id, authorName);
        }
    }

}

