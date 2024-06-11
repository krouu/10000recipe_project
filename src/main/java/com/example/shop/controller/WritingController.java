package com.example.shop.controller;

import com.example.shop.database.Recipes;
import com.example.shop.repository.RecipesRepository;
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
                              @RequestParam("gastronomy") List<String> gastronomy) {
        // 요리법을 엔티티에 설정
        recipes.setGastronomy(gastronomy);

        if (!imageFile.isEmpty()) {
            try {
                // 파일을 저장할 절대 경로 설정
                String uploadDirectory = "/uploads/";
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs(); // 디렉토리 생성
                }

                // 파일 경로 설정
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDirectory, fileName);

                // 파일 저장
                Files.write(filePath, imageFile.getBytes());

                // 파일 경로를 엔티티에 설정
                recipes.setImgefile(filePath.toString());
                recipes.setImgename(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        recipesRepository.save(recipes);
        return "redirect:/";
    }

    @GetMapping("/writing")
    public String showAddRecipeForm(Model model) {
        model.addAttribute("recipes", new Recipes());
        // 이미지 파일 경로를 가져와서 모델에 추가
        model.addAttribute("imagePath", "uploads/default.jpg"); // 기본 이미지 경로 설정 (없는 경우에 대비)
        return "writing";
    }
}
