package com.example.shop.controller;


import com.example.shop.repository.RecipesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final RecipesRepository recipesRepository;

    public HomeController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }
    @GetMapping("/")
    public String Home(Model model) {
        model.addAttribute("recipes", recipesRepository.findAll());
        return "index";
    }
    @GetMapping("/header")
   public String Header(Model model) {
       return "header";
   }
    @GetMapping("/footer")
    public String footer(Model model) {
       return "footer";
   }
}
