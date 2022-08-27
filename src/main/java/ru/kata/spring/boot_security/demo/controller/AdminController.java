package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.getUsers());
        model.addAttribute("modelUser", new User());
        model.addAttribute("rolesList", userService.getRoles());
        return "admin";
    }

    @PostMapping
    public String addUser(@ModelAttribute("modelUser") @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        model.addAttribute("usersList", userService.getUsers());
        model.addAttribute("rolesList", userService.getRoles());
        return userService.addUser(user, bindingResult);
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUserByID(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("rolesList", userService.getRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        return userService.updateUser(user, bindingResult);
    }
}
