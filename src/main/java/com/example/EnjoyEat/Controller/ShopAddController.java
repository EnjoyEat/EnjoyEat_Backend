package com.example.EnjoyEat.Controller;

import com.example.EnjoyEat.DTO.ShopDTO;
import com.example.EnjoyEat.Service.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class ShopAddController {

    @GetMapping("shop/{userid}")
    public ResponseEntity<?> clickTrainLike(@PathVariable Long userId, @RequestBody ShopDTO shopDTO) {
        try {
            return ResponseEntity.ok(ShopAddService.addShop(shopDTO));
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("맛집리스트 추가가 실패되었습니다.", e);
        }
    }
}
