
package com.favor.favor.User;

import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.Reminder.ReminderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(UserRequestDto userRequestDto){
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .userId(userRequestDto.getUserId())
                .name(userRequestDto.getName())
                .role(userRequestDto.getRole())
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

    @Transactional
    public UserDetailResponseDto readUser(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException("회원을 찾지 못했습니다")
        );

        List<ReminderResponseDto> r_list = new ArrayList<>();
        List<Reminder> reminderList = user.getReminderList();
        for(Reminder r : reminderList){
            ReminderResponseDto responseDto = new ReminderResponseDto(r);
            r_list.add(responseDto);
        }

        List<GiftResponseDto> g_list = new ArrayList<>();
        List<Gift> giftList = user.getGiftList();
        for(Gift g : giftList){
            GiftResponseDto responseDto = new GiftResponseDto(g);
            g_list.add(responseDto);
        }

        UserDetailResponseDto dto = new UserDetailResponseDto(user, r_list, g_list);
        return dto;
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
    public List<ReminderResponseDto> readReminderList(Long userNo){
        User user = userRepository.findById(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<ReminderResponseDto> reminderDtoList = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            reminderDtoList.add(dto);
        }
        return reminderDtoList;
    }
}
