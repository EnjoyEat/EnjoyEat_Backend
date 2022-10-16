package com.example.EnjoyEat.Controller;

import com.example.EnjoyEat.DTO.UserDTO;
import com.example.EnjoyEat.Service.ResponseService;
import com.example.EnjoyEat.Service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"유저정보 메서드에 관한 API"})
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        try {
            userService.signUp(userDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("회원가입에 실패했습니다.", e);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> findall() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("유저목록을 추출하는데 실패하였습니다.", e);
        }
    }
}