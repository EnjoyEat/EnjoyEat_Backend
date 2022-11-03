package com.example.EnjoyEat.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @Column(name = "profile_image", unique = true)
    private String profileImage;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Lob
    @Column(nullable = false)
    private String intro;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShopAdd> ShopAddList;
}