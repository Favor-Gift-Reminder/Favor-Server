package com.favor.favor.Service;

import com.favor.favor.Dto.ResultDto.SignUpResultDto;
import com.favor.favor.Dto.UserSignUpRequestDto;
import com.favor.favor.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public int signUp(UserSignUpRequestDto userSignUpRequestDto){

        log.info("회원가입 - 아이디 중복검사");
        if(validateUserId(userSignUpRequestDto)){
            log.info(" 아이디 중복검사 통과");
            return 0;
        }
        else {
            return -1;
        }

    }
    private boolean validateUserId(UserSignUpRequestDto userSignUpRequestDto){
        return userRepository.existsByUserId((userSignUpRequestDto.getUserId()));
    }
}
