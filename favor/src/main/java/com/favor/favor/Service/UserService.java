
package com.favor.favor.Service;

import com.favor.favor.Domain.User;
import com.favor.favor.Dto.UserDto.SignUpRequestDto;
import com.favor.favor.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(SignUpRequestDto signUpRequestDto){
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .userId(signUpRequestDto.getUserId())
                .name(signUpRequestDto.getName())
                .role(signUpRequestDto.getRole())
                .build();
        userRepository.save(user);
    }
}
