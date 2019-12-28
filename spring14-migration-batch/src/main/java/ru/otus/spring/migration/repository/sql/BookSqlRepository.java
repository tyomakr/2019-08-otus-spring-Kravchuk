package ru.otus.spring.migration.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.migration.domain.sql.BookSql;

public interface BookSqlRepository extends JpaRepository<BookSql, Long> {
}
