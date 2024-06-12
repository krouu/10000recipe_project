package com.example.shop.controller;

import com.example.shop.database.Recipes;
import com.example.shop.database.Users;
import com.example.shop.repository.RecipesRepository;
import com.example.shop.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final UsersRepository usersRepository;
    private final RecipesRepository recipesRepository;

    @Autowired
    public AdminController(UsersRepository usersRepository, RecipesRepository recipesRepository) {
        this.usersRepository = usersRepository;
        this.recipesRepository = recipesRepository;
    }

    @GetMapping("/admin")
    public String adminInfo(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"admin".equals(loggedInUser.getUserID())) {
            return "redirect:/login"; // 로그인되지 않았거나 관리자가 아니면 로그인 페이지로 리디렉션
        }
        return "adminInfo"; // 회원관리 페이지로 이동
    }

    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/api/users")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestParam String userId) {
        Users user = usersRepository.findByUserID(userId);
        if (user != null) {
            usersRepository.delete(user);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("fail");
    }

    @GetMapping("/admin/user/{userId}/recipes")
    public String viewUserRecipes(@PathVariable String userId, Model model) {
        Users user = usersRepository.findByUserID(userId);
        if (user == null) {
            return "redirect:/admin"; // 유저가 없으면 관리자 페이지로 리디렉션
        }

        List<Recipes> recipes = recipesRepository.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("recipes", recipes);
        return "userRecipes"; // 유저 레시피 리스트 페이지로 이동
    }

    @GetMapping("/adminHome")
    public String adminHome() {
        return "adminHome"; // 회원관리 페이지로 이동
    }
}
