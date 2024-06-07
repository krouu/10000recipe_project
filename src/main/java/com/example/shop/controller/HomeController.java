package com.example.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(Model model) {
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
