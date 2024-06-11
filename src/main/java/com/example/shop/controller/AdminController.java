package com.example.shop.controller;

import com.example.shop.database.Users;
import com.example.shop.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final UsersRepository usersRepository;

    @Autowired
    public AdminController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/admin")
    public String adminInfo(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"admin".equals(loggedInUser.getUserID())) {
            return "redirect:/login"; // 로그인되지 않았거나 관리자가 아니면 로그인 페이지로 리디렉션
        }

        return "adminInfo"; // 회원관리 페이지로 이동
    }

    @GetMapping("/api/users")
    @ResponseBody
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @DeleteMapping("/api/users")
    @ResponseBody
    public String deleteUser(@RequestParam String userId) {
        Users user = usersRepository.findByUserID(userId);
        if (user != null) {
            usersRepository.delete(user);
            return "success";
        }
        return "fail";
    }


    @GetMapping("/adminHome")
    public String adminHome(HttpSession session, Model model) {
        return "adminHome"; // 회원관리 페이지로 이동
    }
}
