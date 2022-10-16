package com.example.EnjoyEat.DTO;

import com.example.EnjoyEat.Model.ShopAdd;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class UserDTO {

    private Long userId;

    @ApiModelProperty(value = "카카오로그인 임시ID", example = "dudrhkd6550", required = true)
    private String providerId;

    @ApiModelProperty(value = "카카오계정의 이름", example = "테스트유저", required = true)
    private String username;

    @ApiModelProperty(value = "유저 이메일", example = "dudrhkd900@naver.com", required = true)
    private String email;

    @ApiModelProperty(hidden = true, required = false)
    private String profileImage;

    @ApiModelProperty(value = "추가설정한 유저 닉네임", example = "글로리", required = true)
    private String nickname;

    @ApiModelProperty(value = "추가설정한 본인소개글", example = "돈까스러버 입니다 돈까스 좋아하시는 분들 모이세요", required = true)
    private String intro;

    private List<Long> ShopAddDTOList;
}
