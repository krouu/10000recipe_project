package com.example.shop.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recipeID;

    @Column
    private Integer category;

    @ManyToOne
    @JoinColumn(name = "no", nullable = false)
    private Users user;

    @Column(length = 255)
    private String recipeTitle;

    @Column(length = 255)
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String gastronomy;

    @Column(length = 255)
    private String imageURL;
}
