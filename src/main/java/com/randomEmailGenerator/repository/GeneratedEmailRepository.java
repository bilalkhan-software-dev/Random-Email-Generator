package com.randomEmailGenerator.repository;

import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.entity.GeneratedEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedEmailRepository extends JpaRepository<GeneratedEmail,Integer> {

    List<GeneratedEmail> findByUser(User user);

    List<GeneratedEmail> findByUserAndSelected(User user, boolean isSelected);

    boolean existsByEmailAndUser(String email,User user);

}
