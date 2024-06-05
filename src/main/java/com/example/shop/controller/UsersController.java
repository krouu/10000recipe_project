package com.example.shop.controller;

import com.example.shop.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/userss")
    public String getPersons(Model model) {
        model.addAttribute("userss", usersRepository.findAll());
        return "users";
    }
}
