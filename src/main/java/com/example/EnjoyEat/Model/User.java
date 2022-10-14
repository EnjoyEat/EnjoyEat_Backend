package com.example.EnjoyEat.Model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image", unique = true)
    private String profileImage;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String intro;
}
