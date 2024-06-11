package com.example.shop.controller;

import com.example.shop.database.Recipes;
import com.example.shop.database.Users;
import com.example.shop.repository.RecipesRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class WritingController {

    private final RecipesRepository recipesRepository;

    @Autowired
    public WritingController(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @PostMapping("/writing/save")
    public String writingSave(@ModelAttribute Recipes recipes,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @RequestParam("gastronomy") List<String> gastronomy,
                              HttpSession session,
                              Model model) {
        // 로그인된 사용자 확인
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        if (!imageFile.isEmpty()) {
            try {
                // 프로젝트의 static 폴더 하위에 uploads 폴더 경로 설정
                String uploadDirectory = new File("src/main/resources/static/uploads").getAbsolutePath();
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs(); // 디렉토리 생성
                }

                // 파일 경로 설정
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDirectory, fileName);

                // 파일 저장
                Files.write(filePath, imageFile.getBytes());

                // DB에 저장할 파일 경로 설정 (웹 브라우저에서 접근 가능한 경로로 설정)
                String dbFilePath = "/uploads/" + fileName;
                recipes.setImgefile(dbFilePath);
                recipes.setImgename(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
                return "writing"; // 에러 발생 시 다시 작성 페이지로 이동
            }
        } else {
            model.addAttribute("errorMessage", "이미지 파일을 선택해 주세요.");
            return "writing"; // 이미지 파일이 비어 있을 경우 다시 작성 페이지로 이동
        }

        recipesRepository.save(recipes);
        return "redirect:/";
    }

    @GetMapping("/writing")
    public String showAddRecipeForm(HttpSession session, Model model) {
        // 로그인된 사용자 확인
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("recipes", new Recipes());
        // 이미지 파일 경로를 가져와서 모델에 추가
        model.addAttribute("imagePath", "/uploads/default.jpg"); // 기본 이미지 경로 설정 (없는 경우에 대비)
        return "writing";
    }
}
