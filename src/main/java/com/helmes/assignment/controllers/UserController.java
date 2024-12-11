package com.helmes.assignment.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users/new")
    public String newUser() {
        return "users/new";
    }
}
