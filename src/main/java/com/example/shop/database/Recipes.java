package com.example.shop.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipeID;

    @Column
    private Integer category;

    @Column(length = 255)
    private String recipeTitle;

    @Column(length = 255)
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String gastronomy;

    @Column(length = 255)
    private String imgefile;

    @Column(length = 255)
    private String imgename;

    @ManyToOne
    @JoinColumn(name = "NO")
    private Users user;

    // Getters and Setters
}
