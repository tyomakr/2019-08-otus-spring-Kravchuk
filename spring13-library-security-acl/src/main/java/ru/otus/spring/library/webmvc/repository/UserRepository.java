package ru.otus.spring.library.webmvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.library.webmvc.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
