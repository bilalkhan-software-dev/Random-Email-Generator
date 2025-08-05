package com.randomEmailGenerator.repository;

import com.randomEmailGenerator.entity.GeneratedEmail;
import com.randomEmailGenerator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratedEmailRepository extends JpaRepository<GeneratedEmail, Integer> {
    List<GeneratedEmail> findByUserAndIsSelected(User user, boolean isSelected);
    List<GeneratedEmail> findByUser(User user);
}