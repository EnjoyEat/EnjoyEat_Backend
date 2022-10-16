package com.example.EnjoyEat.Controller;

import com.example.EnjoyEat.DTO.UserDTO;
import com.example.EnjoyEat.Service.ResponseService;
import com.example.EnjoyEat.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"유저정보 메서드에 관한 API"})
@RestController
public class UserController {

    @Autowired
    UserService userService;

    // 임시 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.signUp(userDTO));
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("회원가입에 실패했습니다.", e);
        }
    }

    // 모든 회원을 보여주기
    @ApiOperation(value = "모든회원을 보여주는 API")
    @GetMapping("/user")
    public ResponseEntity<?> findall() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("유저목록을 추출하는데 실패하였습니다.", e);
        }
    }

    // 모든 회원을 보여주기
    @ApiOperation(value = "특정회원을 보여주는 API")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findall(@ApiParam(value = "조회 할 유저의 PK") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("특정유저를 추출하는데 실패하였습니다.", e);
        }
    }

    //회원정보 수정
    @ApiOperation(value = "회원정보를 수정하는 API")
    @PatchMapping("/user/{id}")
    public ResponseEntity<?> update(@ApiParam(value = "수정할 유저의 PK") @PathVariable Long id,
                                    @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.update(id, userDTO));
        } catch (Exception e) {
            return ResponseService.makeResponseEntity("회원정보 수정에 실패되었습니다", e);
        }
    }
}