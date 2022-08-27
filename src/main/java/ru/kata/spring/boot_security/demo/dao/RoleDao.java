package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.UserRole;

public interface RoleDao extends CrudRepository<UserRole, Long> {
}
