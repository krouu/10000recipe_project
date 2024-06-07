package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/users/add")
    public String showAddPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "personForm";
    }

    @PostMapping("/users/save")
    public String personSave(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String getPersons(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "persons";
    }
}
