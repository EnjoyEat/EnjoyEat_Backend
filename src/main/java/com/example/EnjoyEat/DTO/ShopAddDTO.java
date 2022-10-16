package com.example.EnjoyEat.DTO;

import com.example.EnjoyEat.Model.User;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ShopAddDTO {
    private Long userId;

    private Long shopId;
    private String address;
    private String regionOne;
    private String regionTwo;
    private String latitude;
    private String longitude;
    private double rating;
    private String content;
}