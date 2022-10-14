package com.example.EnjoyEat.Model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false)
    private String address;

    @Column(name = "region_one", nullable = false)
    private String regionOne;

    @Column(name = "region_two", nullable = false)
    private String regionTwo;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String content;
}
