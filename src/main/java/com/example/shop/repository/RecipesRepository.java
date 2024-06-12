package com.example.shop.repository;

import com.example.shop.database.Recipes;
import com.example.shop.database.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    List<Recipes> findByUser(Users user);
}
