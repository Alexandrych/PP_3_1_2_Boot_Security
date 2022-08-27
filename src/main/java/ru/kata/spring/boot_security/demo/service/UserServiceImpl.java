package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRole;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl (UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public long getUsersCount() {
        return userDao.count();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userDao.findAll().forEach(users::add);
        return users;
    }

    @Override
    @Transactional
    public String addUser(User user, BindingResult bindingResult) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.user", "Аккаунт с указанным логином уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return "redirect:/admin";
    }

    @Override
    @Transactional
    public String updateUser(User user, BindingResult bindingResult) {
        if (userDao.findByUsername(user.getUsername()) != null && user.getId() != userDao.findByUsername(user.getUsername()).getId()) {
            bindingResult.rejectValue("username", "error.user", "Аккаунт с указанным логином уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return "redirect:/admin";
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User getUserByID(long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public Set<UserRole> getRoles() {
        Set<UserRole> userRoles = new HashSet<>();
        roleDao.findAll().forEach(userRoles::add);
        return userRoles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}
