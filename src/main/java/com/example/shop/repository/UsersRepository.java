package com.example.shop.repository;

import com.example.shop.database.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUserIDAndPassword(String userID, String password);

    Boolean existsByUserID(String userID);

    Users findByUserID(String userID);
}
