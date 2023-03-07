package com.favor.favor.User;

import com.favor.favor.Common.Category;
import com.favor.favor.Common.Emotion;
import com.favor.favor.Common.Favor;
import com.favor.favor.Common.Role;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendResponseDto;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Gift.GiftDetailResponseDto;
import com.favor.favor.Gift.GiftRepository;
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
    private final GiftRepository giftRepository;

    public UserDetailResponseDto signUp(signUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return new UserDetailResponseDto(user);
    }

    public UserDetailResponseDto createProfile(profileDto profileDto, Long userNo) {
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        user.setName(profileDto.getName());
        user.setUserId(profileDto.getUserId());
        userRepository.save(user);

        return new UserDetailResponseDto(user);
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

        List<FriendResponseDto> f_list = new ArrayList<>();
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            FriendResponseDto dto = new FriendResponseDto(f);
            f_list.add(dto);
        }

        List<Favor> favor_List = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favor_List.add(Favor.valueOf(favorType));
        }

        UserDetailResponseDto dto = new UserDetailResponseDto(user, r_list, g_list, f_list, favor_List);
        return dto;
    }

    @Transactional
    public UserDetailResponseDto updateUser(Long userNo, UserUpdateRequestDto userUpdateRequestDto){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () ->new RuntimeException("회원을 찾지 못했습니다")
        );
        user.setName(userUpdateRequestDto.getName());
        user.setUserId(userUpdateRequestDto.getUserId());
        user.setFavorList(userUpdateRequestDto.getFavorList());
        userRepository.save(user);

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

        List<FriendResponseDto> f_list = new ArrayList<>();
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            FriendResponseDto dto = new FriendResponseDto(f);
            f_list.add(dto);
        }

        List<Favor> favor_List = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favor_List.add(Favor.valueOf(favorType));
        }

        UserDetailResponseDto userDetailResponseDto = new UserDetailResponseDto(user, r_list, g_list, f_list, favor_List);
        return userDetailResponseDto;
    }

    @Transactional
    public UserDetailResponseDto deleteUser(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        userRepository.deleteById(userNo);

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

        List<FriendResponseDto> f_list = new ArrayList<>();
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            FriendResponseDto dto = new FriendResponseDto(f);
            f_list.add(dto);
        }

        List<Favor> favor_List = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favor_List.add(Favor.valueOf(favorType));
        }

        return new UserDetailResponseDto(user, r_list, g_list, f_list, favor_List);

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
    public List<FriendResponseDto> readFriendList(Long userNo){
        User user = userRepository.findByUserNo(userNo).orElseThrow(
                () -> new RuntimeException()
        );
        List<FriendResponseDto> f_List = new ArrayList<>();
        for(Friend f : user.getFriendList()){
            FriendResponseDto dto = new FriendResponseDto(f);
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
        Integer categoryNo = category.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndCategory(user, categoryNo);
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
        Integer emotionNo = emotion.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndEmotion(user, emotionNo);
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
