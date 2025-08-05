package com.randomEmailGenerator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneratedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private boolean isSelected = false;

    private LocalDateTime createdAt;

}
