package com.example.shop.controller;

import com.example.shop.database.Recipes;
import com.example.shop.database.Users;
import com.example.shop.repository.RecipesRepository;
import com.example.shop.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RecipesController {

    private final RecipesRepository recipesRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RecipesController(RecipesRepository recipesRepository, UsersRepository usersRepository) {
        this.recipesRepository = recipesRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/user/info")
    public UserInfoResponse getUserInfo(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<Recipes> userRecipes = recipesRepository.findByUser(loggedInUser);
        List<RecipeDTO> recipeDTOs = userRecipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getRecipeTitle(),
                        recipe.getCategory(),
                        recipe.getIngredients(),
                        recipe.getGastronomy(),
                        recipe.getImgefile(),
                        recipe.getRecipeID()
                ))
                .collect(Collectors.toList());

        return new UserInfoResponse(
                loggedInUser.getUsername(),
                loggedInUser.getUserID(),
                loggedInUser.getEmail(),
                loggedInUser.getPhoneNumber(),
                recipeDTOs
        );
    }

    @GetMapping("/user/{userId}/recipes")
    public List<RecipeDTO> getUserRecipes(@PathVariable String userId) {
        Users user = usersRepository.findByUserID(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Recipes> userRecipes = recipesRepository.findByUser(user);
        return userRecipes.stream()
                .map(recipe -> new RecipeDTO(
                        recipe.getRecipeTitle(),
                        recipe.getCategory(),
                        recipe.getIngredients(),
                        recipe.getGastronomy(),
                        recipe.getImgefile(),
                        recipe.getRecipeID()
                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Integer id) {
        if (recipesRepository.existsById(id)) {
            recipesRepository.deleteById(id);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(404).body("Recipe not found");
        }
    }

    // DTO 클래스
    public static class UserInfoResponse {
        private String username;
        private String userID;
        private String email;
        private String phoneNumber;
        private List<RecipeDTO> recipes;

        public UserInfoResponse(String username, String userID, String email, String phoneNumber, List<RecipeDTO> recipes) {
            this.username = username;
            this.userID = userID;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.recipes = recipes;
        }

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public List<RecipeDTO> getRecipes() {
            return recipes;
        }

        public void setRecipes(List<RecipeDTO> recipes) {
            this.recipes = recipes;
        }
    }

    public static class RecipeDTO {
        private String recipeTitle;
        private Integer category;
        private String ingredients;
        private String gastronomy;
        private String imgefile;
        private Integer recipeID;

        public RecipeDTO(String recipeTitle, Integer category, String ingredients, String gastronomy, String imgefile, Integer recipeID) {
            this.recipeTitle = recipeTitle;
            this.category = category;
            this.ingredients = ingredients;
            this.gastronomy = gastronomy;
            this.imgefile = imgefile;
            this.recipeID = recipeID;
        }

        // Getters and Setters
        public String getRecipeTitle() {
            return recipeTitle;
        }

        public void setRecipeTitle(String recipeTitle) {
            this.recipeTitle = recipeTitle;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getIngredients() {
            return ingredients;
        }

        public void setIngredients(String ingredients) {
            this.ingredients = ingredients;
        }

        public String getGastronomy() {
            return gastronomy;
        }

        public void setGastronomy(String gastronomy) {
            this.gastronomy = gastronomy;
        }

        public String getImgefile() {
            return imgefile;
        }

        public void setImgefile(String imgefile) {
            this.imgefile = imgefile;
        }

        public Integer getRecipeID() {
            return recipeID;
        }

        public void setRecipeID(Integer recipeID) {
            this.recipeID = recipeID;
        }
    }
}
