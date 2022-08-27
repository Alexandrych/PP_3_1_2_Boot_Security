package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
