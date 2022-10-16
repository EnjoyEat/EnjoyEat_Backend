package com.example.EnjoyEat.Controller;

import com.example.EnjoyEat.DTO.ShopAddDTO;
import com.example.EnjoyEat.Service.ResponseService;
import com.example.EnjoyEat.Service.ShopAddService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"유저가 맛집리스트 추가하는 메서드에 관한 API"})
@RestController
public class ShopAddController {

    @Autowired
    private ShopAddService shopAddService;

    @PostMapping("shop")
    public ResponseEntity<?> addShop(@RequestBody ShopAddDTO shopAddDTO) {
        try {
            return ResponseEntity.ok(shopAddService.addShop(shopAddDTO));
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("맛집리스트 추가가 실패되었습니다.", e);
        }
    }
}
