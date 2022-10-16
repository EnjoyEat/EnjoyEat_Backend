package com.example.EnjoyEat.DTO;

import com.example.EnjoyEat.Model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class ShopAddDTO {

    @ApiModelProperty(value = "사용자 pk", example = "1", required = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private Long shopId;

    @ApiModelProperty(value = "식당의 이름", example = "헤키", required = true)
    private String name;

    @ApiModelProperty(value = "식당의 전체주소", example = "인천광역시 계양구 쌍용아파트", required = true)
    private String address;

    @ApiModelProperty(value = "식당의 주소(시)", example = "인천광역시", required = true)
    private String regionOne;

    @ApiModelProperty(value = "식당의 주소(구)", example = "계양구", required = true)
    private String regionTwo;

    @ApiModelProperty(value = "경도", example = "1651.546.16.161", required = true)
    private String latitude;

    @ApiModelProperty(value = "위도", example = "1651.546.16.161", required = true)
    private String longitude;

    @ApiModelProperty(value = "사용자의 평점", example = "4.0", required = true)
    private double rating;

    @ApiModelProperty(value = "사용자가 평가한 식당후기", example = "맛있었고 친절해서 너무 좋았습니다.", required = true)
    private String content;
}