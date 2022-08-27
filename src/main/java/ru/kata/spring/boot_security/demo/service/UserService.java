package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRole;

import java.util.List;
import java.util.Set;

@Component
public interface UserService extends UserDetailsService {
    long getUsersCount();
    List<User> getUsers();
    Set<UserRole> getRoles();
    String addUser(User user, BindingResult bindingResult);
    String updateUser(User user, BindingResult bindingResult);
    void deleteUserById(long id);
    User getUserByID(long id);
    User findUserByUsername(String username);
    UserDetails loadUserByUsername(String username);
    PasswordEncoder passwordEncoder();
}
