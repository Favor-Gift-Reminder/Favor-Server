
package com.favor.favor.User;

import com.favor.favor.Reminder.Reminder;
import com.favor.favor.Reminder.ReminderListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public List<UserResponseDto> readAll(){
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            UserResponseDto userResponseDto = new UserResponseDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    public UserResponseDto readUser(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException("회원을 찾지 못했습니다")
        );

        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }

    public Long updateUser(Long userNo, UserUpdateRequestDto dto){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () ->new RuntimeException("회원을 찾지 못했습니다")
        );
        user.setName(dto.getName());
        user.setUserId(dto.getUserId());

        userRepository.save(user);
        return userNo;
    }

    public Long deleteUser(Long userNo){
        userRepository.deleteById(userNo);
        return userNo;
    }

    @Transactional
    public List<ReminderListResponseDto> readReminderList(Long userNo){
        User user = userRepository.findById(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<ReminderListResponseDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderListResponseDto dto = new ReminderListResponseDto(r);
            reminderDtoList.add(dto);
        }
        return reminderDtoList;
    }
}
