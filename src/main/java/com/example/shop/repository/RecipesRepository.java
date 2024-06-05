package com.example.shop.repository;

import com.example.shop.database.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipes, Long> {
}