package com.example.shop.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(nullable = false, unique = true)
    private String userID;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private Date age;

    @Column(length = 100)
    private String phoneNumber;

    @Column(length = 100)
    private String email;
}
