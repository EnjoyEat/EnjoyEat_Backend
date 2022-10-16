package com.example.EnjoyEat.DTO;

import com.example.EnjoyEat.Model.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDTO {
    private Long shopId;
    private String address;
    private String regionOne;
    private String regionTwo;
    private String latitude;
    private String longitude;
    private double rating;
    private String content;
    private User user;
}
