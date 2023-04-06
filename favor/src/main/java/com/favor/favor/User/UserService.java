package com.favor.favor.user;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Category;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.gift.*;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;
    private final FriendRepository friendRepository;


    @Transactional
    public User signUp(SignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .role(Role.USER)
                .build();
        save(user);
        return user;
    }



    @Transactional
    public User createProfile(ProfileDto profileDto, Long userNo) {
        User user = findUserByUserNo(userNo);

        user.setName(profileDto.getName());
        user.setUserId(profileDto.getUserId());
        save(user);

        return user;
    }



    @Transactional
    public User readUser(Long userNo){
        return findUserByUserNo(userNo);
    }

    @Transactional
    public User updateUser(User user, UserUpdateRequestDto userUpdateRequestDto){
        user.setName(userUpdateRequestDto.getName());
        user.setUserId(userUpdateRequestDto.getUserId());
        user.setFavorList(userUpdateRequestDto.getFavorList());
        save(user);

        return user;
    }

    @Transactional
    public void deleteUser(Long userNo) {
        User user = findUserByUserNo(userNo);
        friendRepository.deleteFriendsByFriendUserNo(userNo);
        userRepository.deleteByUserNo(userNo);
    }


    public User updatePassword(String email, String password){
        User user = findUserByEmail(email);
        user.setPassword(password);
        save(user);

        return user;
    }


    @Transactional
    public List<ReminderResponseDto> readReminderList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<ReminderResponseDto> r_List = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_List.add(dto);
        }
        return r_List;
    }

    @Transactional
    public List<GiftResponseDto> readGiftList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : user.getGiftList()){
            List<Long> friendNoList = new ArrayList<>();
            for(Long f : g.getFriendNoList()){
                friendNoList.add(f);
            }
            GiftResponseDto dto = new GiftResponseDto(g, friendNoList);
            g_List.add(dto);
        }
        return g_List;
    }

    @Transactional
    public List<FriendResponseDto> readFriendList(Long userNo){
        User user = findUserByUserNo(userNo);

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
            UserResponseDto userResponseDto = returnUserDto(user);
            u_List.add(userResponseDto);
        }
        return u_List;
    }




    public List<GiftResponseDto> readGiftListByName(Long userNo, String giftName){
        User user = findUserByUserNo(userNo);

        List<Gift> giftList = giftRepository.findGiftsByUserAndGiftNameContains(user, giftName);
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            List<Long> friendNoList = new ArrayList<>();
            for(Long f : g.getFriendNoList()){
                friendNoList.add(f);
            }
            GiftResponseDto dto = new GiftResponseDto(g, friendNoList);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftResponseDto> readGiftListByCategory(Long userNo, Category category){
        User user = findUserByUserNo(userNo);

        Integer categoryNo = category.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndCategory(user, categoryNo);
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            List<Long> friendNoList = new ArrayList<>();
            for(Long f : g.getFriendNoList()){
                friendNoList.add(f);
            }
            GiftResponseDto dto = new GiftResponseDto(g, friendNoList);
            g_List.add(dto);
        }

        return g_List;
    }

    public List<GiftResponseDto> readGiftListByEmotion(Long userNo, Emotion emotion){
        User user = findUserByUserNo(userNo);

        Integer emotionNo = emotion.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndEmotion(user, emotionNo);
        List<GiftResponseDto> g_List = new ArrayList<>();
        for(Gift g : giftList){
            List<Long> friendNoList = new ArrayList<>();
            for(Long f : g.getFriendNoList()){
                friendNoList.add(f);
            }
            GiftResponseDto dto = new GiftResponseDto(g, friendNoList);
            g_List.add(dto);
        }

        return g_List;
    }




    @Transactional
    public User save(User user){
        try{
            return userRepository.save(user);
        } catch(RuntimeException e){
            //500
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    //VALIDATE
    public void validateExistingEmail (String email){
        Boolean isExistingEmail = null;
        try{
            isExistingEmail = userRepository.existsByEmail(email);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingEmail){
            throw new CustomException(null, EMAIL_NOT_FOUND);
        }
    }



    //FIND
    public User findUserByUserNo(Long userNo){
        User user = null;
        try{
            user = userRepository.findByUserNo(userNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    public User findUserByUserId(String userId){
        User user = null;
        try{
            user = userRepository.findByUserId(userId).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }
    public User findUserByEmail(String email){
        User user = null;
        try{
            user = userRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }



    //RETURN
    @Transactional
    public UserResponseDto returnUserDto(User user){

        List<ReminderResponseDto> r_list = new ArrayList<>();
        List<Reminder> reminderList = user.getReminderList();
        for(Reminder r : reminderList){
            ReminderResponseDto dto = new ReminderResponseDto(r);
            r_list.add(dto);
        }
        List<GiftResponseDto> g_list = new ArrayList<>();
        List<Gift> giftList = user.getGiftList();
        for(Gift g : giftList){
            List<Long> friendNoList = new ArrayList<>();
            for(Long f : g.getFriendNoList()){
                friendNoList.add(f);
            }
            GiftResponseDto dto = new GiftResponseDto(g, friendNoList);
            g_list.add(dto);
        }

        List<FriendResponseDto> f_list = new ArrayList<>();
        List<Friend> friendList = user.getFriendList();
        for(Friend f : friendList){
            FriendResponseDto dto = new FriendResponseDto(f);
            f_list.add(dto);
        }

        List<AnniversaryResponseDto> a_List = new ArrayList<>();
        List<Anniversary> anniversaryList = user.getAnniversaryList();
        for(Anniversary a : anniversaryList){
            AnniversaryResponseDto dto = new AnniversaryResponseDto(a);
            a_List.add(dto);
        }

        List<Favor> favor_List = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favor_List.add(Favor.valueOf(favorType));
        }

        UserResponseDto dto = new UserResponseDto(user, r_list, g_list, f_list, favor_List, a_List);
        return dto;
    }



    //IS_EXISTING
    public void isExistingUserId (String id){
        Boolean isExistingId = null;
        try{
            isExistingId = userRepository.existsByUserId(id);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(isExistingId){
            throw new CustomException(null, DUPLICATE_ID);
        }
    }

    public void isExistingEmail (String email){
        Boolean isExistingEmail = null;
        try{
            isExistingEmail = userRepository.existsByEmail(email);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(isExistingEmail){
            throw new CustomException(null, DUPLICATE_EMAIL);
        }
    }

    public void isExistingUserNo (Long userNo){
        Boolean isExistingUser = null;
        try{
            isExistingUser = userRepository.existsByUserNo(userNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingUser){
            throw new CustomException(null, USER_NOT_FOUND);
        }
    }

}
