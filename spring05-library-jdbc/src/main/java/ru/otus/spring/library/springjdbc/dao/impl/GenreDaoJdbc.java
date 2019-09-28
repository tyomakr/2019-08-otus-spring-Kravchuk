package ru.otus.spring.library.springjdbc.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.springjdbc.dao.GenreDao;
import ru.otus.spring.library.springjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Genre getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("SELECT * FROM genres WHERE id = :id", params, new GenreMapper());
    }


    @Override
    public Genre getByName(String genreName) {
        Map<String, Object> params = Collections.singletonMap("genreName", genreName);
        return jdbc.queryForObject("SELECT * FROM genres WHERE (genreName) = :genreName", params, new GenreMapper());
    }


    @Override
    public List<Genre> getAll() {
        return jdbc.getJdbcOperations().query("SELECT * FROM genres", new GenreMapper());
    }


    @Override
    public void insert(Genre genre) {
        jdbc.getJdbcOperations().update("INSERT INTO GENRES (GENRENAME) values ( ? )", genre.getGenreName());
    }


    @Override
    public void update(Genre oldGenre, String newGenreName) {
        jdbc.getJdbcOperations().update("UPDATE GENRES set GENRENAME = ? where ID = ?", newGenreName, oldGenre.getId());
    }


    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String genreName = resultSet.getString("genreName");
            return new Genre(id, genreName);
        }
    }

}
