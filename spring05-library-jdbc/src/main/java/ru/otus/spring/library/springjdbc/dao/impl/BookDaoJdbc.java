package ru.otus.spring.library.springjdbc.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.springjdbc.dao.AuthorDao;
import ru.otus.spring.library.springjdbc.dao.BookDao;
import ru.otus.spring.library.springjdbc.dao.GenreDao;
import ru.otus.spring.library.springjdbc.domain.Author;
import ru.otus.spring.library.springjdbc.domain.Book;
import ru.otus.spring.library.springjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    @Override
    public Book getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
            return jdbc.queryForObject("SELECT * FROM books WHERE id = :id", params, new BookMapper(authorDao, genreDao));
    }

    @Override
    public Book getByName(String bookName, int authorId) {
        Map<String, Object> params = Collections.singletonMap("bookName", bookName);
        return jdbc.queryForObject("SELECT * FROM books WHERE (bookName) = :bookName and (AUTHORID) = authorId", params, new BookMapper(authorDao, genreDao));
    }


    @Override
    public List<Book> getAll() {
        return jdbc.getJdbcOperations().query("SELECT * FROM books ORDER BY BOOKNAME", new BookMapper(authorDao, genreDao));
    }


    @Override
    public void insert(Book book) {
        int authorId = authorDao.getByName(book.getAuthor().getAuthorName()).getId();
        int genreId = genreDao.getByName(book.getGenre().getGenreName()).getId();
        jdbc.getJdbcOperations().update("INSERT INTO BOOKS (BOOKNAME, AUTHORID, GENREID) VALUES ( ?, ?, ? )", book.getBookName(), authorId, genreId);
    }


    @Override
    public void update(Book book, String newBookName) {
        jdbc.getJdbcOperations().update("UPDATE books set BOOKNAME = ? where ID = ?", newBookName, book.getId());
    }

    @Override
    public void updateBookAuthor(Book book, int oldAuthorId, int newAuthorId) {
        jdbc.getJdbcOperations().update("UPDATE BOOKS set AUTHORID = ? WHERE BOOKNAME = ? AND AUTHORID = ?", newAuthorId, book.getBookName(), oldAuthorId);
    }

    @Override
    public void updateBookGenre(int bookId, int newGenreId) {
        jdbc.getJdbcOperations().update("UPDATE BOOKS set GENREID = ? where ID = ?", newGenreId, bookId);
    }


    @Override
    public void deleteById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("DELETE FROM books WHERE id = :id", params);
    }


    @RequiredArgsConstructor
    private static class BookMapper implements RowMapper<Book> {

        private final AuthorDao authorDao;
        private final GenreDao genreDao;

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String bookName = resultSet.getString("bookName");
            int authorId = resultSet.getInt("authorId");
            int genreId = resultSet.getInt("genreId");
            Author author = authorDao.getById(authorId);
            Genre genre = genreDao.getById(genreId);

            return new Book(id, bookName, author, genre);
        }
    }
}
