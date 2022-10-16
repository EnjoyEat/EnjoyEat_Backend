package com.example.EnjoyEat.DTO;

import com.example.EnjoyEat.Model.ShopAdd;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class UserDTO {

    private Long userId;
    private String providerId;
    private String username;
    private String email;
    private String profileImage;
    private String nickname;
    private String intro;

    private List<Long> ShopAddDTOList;
}
