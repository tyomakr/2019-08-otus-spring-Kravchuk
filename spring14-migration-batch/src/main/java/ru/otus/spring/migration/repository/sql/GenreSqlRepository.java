package ru.otus.spring.migration.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.migration.domain.sql.GenreSql;

public interface GenreSqlRepository extends JpaRepository<GenreSql, Long> {
}
