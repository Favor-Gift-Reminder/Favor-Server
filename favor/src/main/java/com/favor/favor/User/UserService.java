package com.favor.favor.User;

import com.favor.favor.Common.Category;
import com.favor.favor.Common.Emotion;
import com.favor.favor.Common.Favor;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendListResponseDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftDetailResponseDto;
import com.favor.favor.Gift.GiftRepository;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.Reminder.ReminderResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;

    public Long signUp(UserRequestDto userRequestDto) {
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

        List<Favor> favor_List = new ArrayList<>();
        for(Favor f : user.getFavorList()){
            favor_List.add(f);
        }

        UserDetailResponseDto dto = new UserDetailResponseDto(user, r_list, g_list, f_list, favor_List);
        return dto;
    }

    public Long updateUser(Long userNo, UserUpdateRequestDto dto){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () ->new RuntimeException("회원을 찾지 못했습니다")
        );
        user.setName(dto.getName());
        user.setUserId(dto.getUserId());
        user.setFavorList(dto.getFavorList());

        userRepository.save(user);
        return userNo;
    }

    public Long deleteUser(Long userNo){
        userRepository.deleteById(userNo);
        return userNo;
    }


    @Transactional
    public List<ReminderResponseDto> readReminderList(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<ReminderResponseDto> r_List = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_List.add(dto);
        }
        return r_List;
    }

    @Transactional
    public List<GiftResponseDto> readGiftList(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : user.getGiftList()){
            GiftResponseDto dto = new GiftResponseDto(g);
            g_List.add(dto);
        }
        return g_List;
    }

    @Transactional
    public List<FriendListResponseDto> readFriendList(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<FriendListResponseDto> f_List = new ArrayList<>();
        for(Friend f : user.getFriendList()){
            FriendListResponseDto dto = new FriendListResponseDto(f);
            f_List.add(dto);
        }
        return f_List;
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




    public List<GiftDetailResponseDto> readGiftListByName(Long userNo, String giftName){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndGiftName(user, giftName);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftDetailResponseDto> readGiftListByCategory(Long userNo, Category category){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndCategory(user, category);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftDetailResponseDto> readGiftListByEmotion(Long userNo, Emotion emotion){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<Gift> giftList = giftRepository.findGiftsByUserAndEmotion(user, emotion);
        List<GiftDetailResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            GiftDetailResponseDto dto = new GiftDetailResponseDto(g);
            g_List.add(dto);
        }

        return g_List;
    }

    public UserResponseDto readUserByUserId(String userId){
        User user = userRepository.findUserByUserId(userId).orElseThrow(
                () -> new RuntimeException()
        );
        UserResponseDto dto = new UserResponseDto(user);
        return dto;
    }
}
