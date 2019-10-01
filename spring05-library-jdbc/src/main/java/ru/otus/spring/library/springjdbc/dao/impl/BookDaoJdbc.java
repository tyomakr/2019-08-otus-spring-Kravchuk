package ru.otus.spring.library.springjdbc.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.library.springjdbc.dao.BookDao;
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

    @Override
    public Book getById(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject
                ("SELECT * FROM books as b " +
                        "INNER JOIN authors as a on b.authorid = a.id " +
                        "INNER JOIN genres as g on b.genreid = g.id " +
                        "WHERE b.id = :id", params, new BookMapper());
    }

    @Override
    public Book getByName(String bookName, long authorId) {
        Map<String, Object> params = Collections.singletonMap("bookName", bookName);
        return jdbc.queryForObject
                ("SELECT * FROM books as b " +
                        "INNER JOIN authors as a on b.authorid = a.id " +
                        "INNER JOIN genres as g on b.genreid = g.id " +
                        "WHERE (bookName) = :bookName and (AUTHORID) = authorId", params, new BookMapper());
    }


    @Override
    public List<Book> getAll() {
        return jdbc.getJdbcOperations().query
                ("SELECT b.id, b.bookname, b.authorid, b.genreid, a.authorname, g.genrename " +
                        "FROM books as b " +
                        "INNER JOIN authors as a on b.authorid = a.id " +
                        "INNER JOIN genres as g on b.genreid = g.id " +
                        "ORDER BY BOOKNAME;",
                        new BookMapper());
    }

    @Override
    public void insert(Book book) {
        jdbc.getJdbcOperations().update("INSERT INTO BOOKS (BOOKNAME, AUTHORID, GENREID) VALUES ( ?, ?, ? )", book.getBookName(), book.getAuthor().getId(), book.getGenre().getId());
    }


    @Override
    public void update(Book book, String newBookName) {
        jdbc.getJdbcOperations().update("UPDATE books set BOOKNAME = ? where ID = ?", newBookName, book.getId());
    }

    @Override
    public void updateBookAuthor(Book book, long oldAuthorId, long newAuthorId) {
        jdbc.getJdbcOperations().update("UPDATE BOOKS set AUTHORID = ? WHERE BOOKNAME = ? AND AUTHORID = ?", newAuthorId, book.getBookName(), oldAuthorId);
    }

    @Override
    public void updateBookGenre(long bookId, long newGenreId) {
        jdbc.getJdbcOperations().update("UPDATE BOOKS set GENREID = ? where ID = ?", newGenreId, bookId);
    }


    @Override
    public void deleteById(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("DELETE FROM books WHERE id = :id", params);
    }


    @RequiredArgsConstructor
    private static class BookMapper implements RowMapper<Book> {


        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String bookName = resultSet.getString("bookName");
            Author author = new Author(resultSet.getLong("authorid"), resultSet.getString("authorname"));
            Genre genre = new Genre(resultSet.getLong("genreid"), resultSet.getString("genrename"));

            return new Book(id, bookName, author, genre);
        }
    }
}
