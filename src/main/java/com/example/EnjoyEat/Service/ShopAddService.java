package com.example.EnjoyEat.Service;

import com.example.EnjoyEat.DTO.ShopAddDTO;
import com.example.EnjoyEat.Model.ShopAdd;
import com.example.EnjoyEat.Model.User;
import com.example.EnjoyEat.Repository.ShopRepository;
import com.example.EnjoyEat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ShopAddService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    public String addShop(ShopAddDTO shopAddDTO) {
        User user = userRepository.getReferenceById(shopAddDTO.getUserId());

        ShopAdd shopAdd = shopRepository.findByUserAndShopId(user, shopAddDTO.getShopId());
        if (shopAdd == null) {
            return add(user, shopAddDTO);
        }
        return cancel(shopAdd);
    }

    private String add(User user, ShopAddDTO shopAddDTO) {
        ShopAdd shopAdd = ShopAdd.builder()
                .user(user)
                .shopId(shopAddDTO.getShopId())
                .address(shopAddDTO.getAddress())
                .regionOne(shopAddDTO.getRegionOne())
                .regionTwo(shopAddDTO.getRegionTwo())
                .latitude(shopAddDTO.getLatitude())
                .longitude(shopAddDTO.getLongitude())
                .rating(shopAddDTO.getRating())
                .content(shopAddDTO.getContent())
                .build();
        shopRepository.save(shopAdd);
        return "고객님의 맛집리스트에서 추가되었습니다.";
    }

    private String cancel(ShopAdd shopAdd) {
        shopRepository.delete(shopAdd);
        return "고객님의 맛집리스트에서 제거되었습니다.";
    }
}
