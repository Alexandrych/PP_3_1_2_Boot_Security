package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRole;

@Configuration
public class AdminConfig {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminConfig(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    protected void addAdminIfNotExist() {
        if (userDao.findByUsername("admin") == null) {
            createAdmin();
        }
    }

    protected void createAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        roleDao.save(new UserRole(1L, "ROLE_ADMIN"));
        roleDao.save(new UserRole (2L, "ROLE_USER"));
        user.addRole(new UserRole (1L, "ROLE_ADMIN"));
        user.addRole(new UserRole (2L, "ROLE_USER"));
        userDao.save(user);
    }
}
