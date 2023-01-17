package com.favor.favor.Controller;

import com.favor.favor.Dto.UserSignUpRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Favor")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    @ApiOperation("User 생성 (로그인 구현 전 임시)")
    @PostMapping
    public void signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto){
        log.info("[SignUp] 회원가입을 시작합니다.");

    }
}
