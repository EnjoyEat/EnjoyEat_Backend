package com.example.EnjoyEat.Service;

import com.example.EnjoyEat.DTO.UserDTO;
import com.example.EnjoyEat.Model.ShopAdd;
import com.example.EnjoyEat.Model.User;
import com.example.EnjoyEat.Repository.ShopRepository;
import com.example.EnjoyEat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopRepository shopRepository;

    public UserDTO signUp(UserDTO userDTO) {
        User user = makeUser(userDTO);
        user = userRepository.save(user);
        return makeUserDTO(user);
    }

    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(makeUserDTO(user));
        }
        return userDTOList;
    }

    public UserDTO update(Long id, UserDTO userDTO) throws Exception {
        User user = userRepository.getById(id);

        user.setProviderId(userDTO.getProviderId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setProfileImage(userDTO.getProfileImage());
        user.setNickname(userDTO.getNickname());
        user.setIntro(userDTO.getIntro());
        user = userRepository.save(user);
        return makeUserDTO(user);
    }

    private User makeUser(UserDTO userDTO) {
          List<ShopAdd> ShopAddDTOList = new ArrayList<>();

        if (userDTO.getShopAddDTOList() != null) {
            for (Long shopAddId : userDTO.getShopAddDTOList()) {
                ShopAddDTOList.add(shopRepository.getReferenceById(shopAddId));
            }
        }

        return User.builder()
                .providerId(userDTO.getProviderId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .profileImage(userDTO.getProfileImage())
                .nickname(userDTO.getNickname())
                .intro(userDTO.getIntro())
                .ShopAddList(ShopAddDTOList)
                .build();
    }

    public UserDTO makeUserDTO(User user) {

        List<Long> ShopAddIdList = new ArrayList<>();

        if (user.getShopAddList() != null) {
            for (ShopAdd shopAdd : user.getShopAddList()) {
                ShopAddIdList.add(shopAdd.getShopId());
            }
        }

        return UserDTO
                .builder()
                .userId(user.getUserId())
                .providerId(user.getProviderId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .nickname(user.getNickname())
                .intro(user.getIntro())
                .ShopAddDTOList(ShopAddIdList)
                .build();
    }
}
