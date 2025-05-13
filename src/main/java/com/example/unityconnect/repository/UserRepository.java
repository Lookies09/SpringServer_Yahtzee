package com.example.unityconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.unityconnect.entity.User;
import java.util.List;


public interface  UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByNameAndPw(String name, String pw);
}
