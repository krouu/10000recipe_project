package com.example.shop.database;

import jakarta.persistence.*;
import lombok.Getter;
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
    private Integer category; // 양식 중식 한식

    @Column(length = 255)
    private String recipeTitle; // 레시피 제목

    @Column(length = 255)
    private String ingredients; // 재료

    @Column(columnDefinition = "TEXT")
    private String gastronomy; // 요리법 요리순서

    @Column(length = 255)
    private String imgename;

    @Column(length = 255)
    private String imgefile; // 이 필드를 이미지 파일 경로로 사용

    @ManyToOne
    @JoinColumn(name = "NO")
    private Users user;
}
