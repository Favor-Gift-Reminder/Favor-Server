
package com.favor.favor.User;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendListResponseDto;
import com.favor.favor.Friend.FriendResponseDto;
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

    public Long signUp(UserRequestDto userRequestDto){
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .userId(userRequestDto.getUserId())
                .name(userRequestDto.getName())
                .role(userRequestDto.getRole())
                .build();
        userRepository.save(user);
        return user.getUserNo();
    }

    @Transactional
    public UserDetailResponseDto readUser(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException("회원을 찾지 못했습니다")
        );

        List<ReminderResponseDto> r_list = new ArrayList<>();
        List<Reminder> reminderList = user.getReminderList();
        for(Reminder r : reminderList){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_list.add(dto);
        }

        List<GiftResponseDto> g_list = new ArrayList<>();
        List<Gift> giftList = user.getGiftList();
        for(Gift g : giftList){
            GiftResponseDto dto = new GiftResponseDto(g);
            g_list.add(dto);
        }

        List<FriendListResponseDto> f_list = new ArrayList<>();
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            FriendListResponseDto dto = new FriendListResponseDto(f);
            f_list.add(dto);
        }

        UserDetailResponseDto dto = new UserDetailResponseDto(user, r_list, g_list, f_list);
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

    public List<UserResponseDto> readAll(){
        List<UserResponseDto> u_List = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            UserResponseDto userResponseDto = new UserResponseDto(user);
            u_List.add(userResponseDto);
        }
        return u_List;
    }
}
