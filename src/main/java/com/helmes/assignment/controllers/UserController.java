package com.helmes.assignment.controllers;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.enums.Role;
import com.helmes.assignment.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private static final String ALERT_TYPE = "alertType";
    private static final String ALERT_MESSAGE = "alertMessage";
    private static final String ALERT_SUCCESS = "success";
    private static final String ALERT_ERROR = "error";
    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String ROLES = "roles";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/new")
    public String newUser (Model model){
        model.addAttribute(USER, new MyUser());
        model.addAttribute(ROLES, Role.values());
        return "admin/user/new";
    }

    @PostMapping("/users")
    public String saveUser (Model model, @ModelAttribute(USER) MyUser user){
        model.addAttribute(USER, user);
        try {
            userService.createUser(user);
            model.addAttribute(ALERT_TYPE, ALERT_SUCCESS);
            model.addAttribute(ALERT_MESSAGE, "User created successfully");
        } catch (Exception e) {
            model.addAttribute(ALERT_TYPE, ALERT_ERROR);
            model.addAttribute(ALERT_MESSAGE, "User creation failed! " + e.getMessage());
        }
        return "admin/user/new";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser (Model model, @PathVariable Long id){
        model.addAttribute(USER, userService.loadUserById(id));
        model.addAttribute(ROLES, Role.values());
        return "admin/user/edit";
    }

    @PostMapping("/admin/user/update")
    public String updateUser (RedirectAttributes redirectAttributes, @ModelAttribute(USER) MyUser user){
        try {
            userService.updateUser(user);
            redirectAttributes.addAttribute(ALERT_TYPE,ALERT_SUCCESS);
            redirectAttributes.addAttribute(ALERT_MESSAGE,
                "User updated successfully");
        } catch (Exception e) {
            redirectAttributes.addAttribute(ALERT_TYPE,ALERT_ERROR);
            redirectAttributes.addAttribute(ALERT_MESSAGE,
                "User creation failed! " + e.getMessage());
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser (@PathVariable Long id, RedirectAttributes redirectAttributes){
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_SUCCESS);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE,"User deleted successfully");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_ERROR);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE,
                "User delete operation failed! " + e.getMessage());
        }
        return "redirect:/admin/home";
    }
}