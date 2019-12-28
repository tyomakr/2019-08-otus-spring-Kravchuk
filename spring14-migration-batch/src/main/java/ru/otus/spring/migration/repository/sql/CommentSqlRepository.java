package ru.otus.spring.migration.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.migration.domain.sql.CommentSql;

public interface CommentSqlRepository extends JpaRepository<CommentSql, Long> {
}
