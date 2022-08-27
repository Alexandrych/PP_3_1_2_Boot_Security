package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("rolesList", userService.getRoles());
        model.addAttribute("yourPage", "Это ваша страница!");
        return "user";
    }

    @GetMapping("/{id}")
    public String getUserByID(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("rolesList", userService.getRoles());
        model.addAttribute("yourPage", "Это страница " + userService.getUserByID(id).getUsername());
        return "user";
    }
}
