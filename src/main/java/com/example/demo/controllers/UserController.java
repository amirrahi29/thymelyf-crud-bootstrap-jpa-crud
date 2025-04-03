package com.example.demo.controllers;

import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        model.addAttribute("user", new UserEntity());  // <-- Add this line
        return "index";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") UserEntity user, RedirectAttributes redirectAttributes) {
        if (user.getId() == null) {
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
        } else {
            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        UserEntity user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/";
    }
}
