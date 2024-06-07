package com.example.shop.controller;

import com.example.shop.database.Recipes;

import com.example.shop.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WritingController {

    private final RecipesRepository recipesRepository;

    @Autowired
    public WritingController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @PostMapping("/writing/save")
    public String writingSave(@ModelAttribute Recipes recipes) {
        recipesRepository.save(recipes);
        return "redirect:/writing";
    }

    @GetMapping("/writing")
    public String showAddPersonForm(Model model) {
        model.addAttribute("recipes", new Recipes());
        return "writing";
    }
}
