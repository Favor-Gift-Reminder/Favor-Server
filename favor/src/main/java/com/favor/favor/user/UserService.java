package com.favor.favor.user;

import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.auth.JwtTokenProvider;
import com.favor.favor.common.enums.CategoryGift;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.gift.*;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderRepository;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.reminder.ReminderSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;
    private final FriendRepository friendRepository;
    private final ReminderRepository reminderRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;



    @Transactional
    public User signUp(SignDto signDto) {

        User user = User.builder()
                .name("Favor00")
                .userId("Favor00")
                .email(signDto.getEmail())
                .password(passwordEncoder.encode(signDto.getPassword()))
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

    public SignInResponseDto signIn(SignDto dto){
        String email = dto.getEmail();
        String password = dto.getPassword();

        isExistingUserByEmail(email);
        User user = findUserByEmail(email);

        isRightPassword(password, user);

        String token = jwtTokenProvider.createToken(user.getUserNo().toString(), user.getRole());

        return new SignInResponseDto(token);
    }


    public void isRightPassword(String password, User user){
        try{
            log.info("password = " + password);
            log.info(("user.getPassword() = " + user.getPassword()));
            log.info("isright = " + passwordEncoder.matches(password, user.getPassword())
            );
            if(!passwordEncoder.matches(password, user.getPassword())){
                throw new RuntimeException();
            }

        }catch (RuntimeException e){
            throw new CustomException(e, PASSWORD_NOT_FOUND);
        }
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
        friendRepository.deleteFriendsByFriendUserNo(userNo);
        userRepository.deleteByUserNo(userNo);
    }


    public User updatePassword(String email, String password){
        User user = findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        save(user);

        return user;
    }


    @Transactional
    public List<ReminderSimpleDto> readReminderList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<ReminderSimpleDto> r_List = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderSimpleDto dto = new ReminderSimpleDto(r);
            r_List.add(dto);
        }
        return r_List;
    }

    @Transactional
    public List<AnniversaryResponseDto> readAnniversaryList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<AnniversaryResponseDto> r_List = new ArrayList<>();
        for(Anniversary r : user.getAnniversaryList()){
            AnniversaryResponseDto dto = new AnniversaryResponseDto(r);
            r_List.add(dto);
        }
        return r_List;
    }

    @Transactional
    public List<GiftSimpleDto> readGiftList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<GiftSimpleDto> g_List = new ArrayList<>();
        for(Gift gift : user.getGiftList()){
            g_List.add(new GiftSimpleDto(gift));
        }
        return g_List;
    }
    @Transactional
    public List<FriendSimpleDto> readFriendList(Long userNo){
        User user = findUserByUserNo(userNo);

        List<FriendSimpleDto> f_List = new ArrayList<>();
        for(Friend f : user.getFriendList()){
            User friendUser = findUserByUserNo(f.getFriendUserNo());
            UserPhoto photo = friendUser.getUserProfilePhoto();
            FriendSimpleDto dto = new FriendSimpleDto(f, friendUser, photo);
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




    public List<GiftSimpleDto> readGiftListByName(Long userNo, String giftName){
        User user = findUserByUserNo(userNo);

        List<Gift> giftList = giftRepository.findGiftsByUserAndGiftNameContains(user, giftName);
        List<GiftSimpleDto> g_List = new ArrayList<>();
        for(Gift gift : giftList){
            g_List.add(new GiftSimpleDto(gift));
        }

        return g_List;
    }

    public List<GiftSimpleDto> readGiftListByCategory(Long userNo, CategoryGift categoryGift){
        User user = findUserByUserNo(userNo);
        Integer categoryNo = categoryGift.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndCategory(user, categoryNo);
        List<GiftSimpleDto> g_List = new ArrayList<>();
        for(Gift gift : giftList){
            g_List.add(new GiftSimpleDto(gift));
        }

        return g_List;
    }

    public List<GiftSimpleDto> readGiftListByEmotion(Long userNo, Emotion emotion){
        User user = findUserByUserNo(userNo);

        Integer emotionNo = emotion.getType();
        List<Gift> giftList = giftRepository.findGiftsByUserAndEmotion(user, emotionNo);
        List<GiftSimpleDto> g_List = new ArrayList<>();
        for(Gift gift : giftList){
            g_List.add(new GiftSimpleDto(gift));
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
    public Friend findFriendByFriendNo(Long friendNo){
        Friend friend = null;
        try{
            friend = friendRepository.findByFriendNo(friendNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, FRIEND_NOT_FOUND);
        }
        return friend;
    }
    public List<ReminderSimpleDto> readReminderListByFMonthAndYear(Long userNo, int year, int month){

        try{
            LocalDate.of(year, month, 1);
        } catch(Exception e){
            throw new CustomException(e, DATE_INVALID);
        }

        List<ReminderSimpleDto> reminderDtoList = new ArrayList<>();
        List<Reminder> reminderList = findReminderListByMonthAndYear(year, month);

        for(Reminder r : reminderList){
            ReminderSimpleDto dto = new ReminderSimpleDto(r);
            if(dto.getUserNo()==userNo) reminderDtoList.add(dto);
        }

        return reminderDtoList;
    }
    public List<Reminder> findReminderListByMonthAndYear(int year, int month){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        return reminderRepository.findAllByReminderDateBetween(start, end);
    }

    public List<GiftSimpleDto> findGivenGiftList(Long userNo){
        User user = findUserByUserNo(userNo);
        List<Gift> giftList = giftRepository.findGiftsByUser(user);
        List<GiftSimpleDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(gift.getIsGiven()){
                GiftSimpleDto dto = new GiftSimpleDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }
    public List<GiftSimpleDto> findReceivedGiftList(Long userNo){
        User user = findUserByUserNo(userNo);
        List<Gift> giftList = giftRepository.findGiftsByUser(user);
        List<GiftSimpleDto> giftResponseDtoList = new ArrayList<>();
        for(Gift gift : giftList){
            if(!gift.getIsGiven()){
                GiftSimpleDto dto = new GiftSimpleDto(gift);
                giftResponseDtoList.add(dto);
            }
        }
        return giftResponseDtoList;
    }



    //RETURN

    @Transactional
    public UserResponseDto returnUserDto(User user){

        List<ReminderSimpleDto> r_List = new ArrayList<>();
        for(Reminder r : user.getReminderList()){
            ReminderSimpleDto dto = new ReminderSimpleDto(r);
            r_List.add(dto);
        }

        List<FriendSimpleDto> f_List = new ArrayList<>();
        for(Friend f : user.getFriendList()){
            User friendUser = findUserByUserNo(f.getFriendUserNo());
            UserPhoto photo = friendUser.getUserProfilePhoto();
            FriendSimpleDto dto = new FriendSimpleDto(f, friendUser, photo);
            f_List.add(dto);
        }

        List<AnniversaryResponseDto> a_List = new ArrayList<>();
        for(Anniversary a : user.getAnniversaryList()){
            AnniversaryResponseDto dto = new AnniversaryResponseDto(a);
            a_List.add(dto);
        }

        List<Favor> favor_List = new ArrayList<>();
        for(Integer favorType : user.getFavorList()){
            favor_List.add(Favor.valueOf(favorType));
        }

        HashMap<String, Integer> giftInfo = returnGiftInfo(user.getUserNo());

        UserResponseDto dto = new UserResponseDto(user, r_List, f_List, favor_List, a_List, giftInfo);
        return dto;
    }

    @Transactional
    public GiftResponseDto returnGiftDto(Gift gift){

        List<Long> friendNoList = gift.getFriendNoList();
        List<FriendSimpleDto> friendResponseDtoList = new ArrayList<>();

        for(Long friendNo : friendNoList){
            Friend friend = findFriendByFriendNo(friendNo);
            User friendUser = findUserByUserNo(friend.getFriendUserNo());
            UserPhoto photo = friendUser.getUserProfilePhoto();
            FriendSimpleDto dto = new FriendSimpleDto(friend, friendUser, photo);
            friendResponseDtoList.add(dto);
        }

        giftRepository.save(gift);
        log.info("[SYSTEM] giftRepository.save(gift) 완료");

        return new GiftResponseDto(gift, friendResponseDtoList);
    }

    public HashMap<String, Integer> returnGiftInfo(Long userNo){
        User user = findUserByUserNo(userNo);

        HashMap<String, Integer> hashMap = new HashMap<>();
        List<Gift> giftList = giftRepository.findGiftsByUser(user);
        hashMap.put("total", giftList.size());

        int given = 0;
        int received = 0;
        for(Gift gift : giftList){
            if(gift.getIsGiven()) given++;
            else received++;
        }
        hashMap.put("given", given);
        hashMap.put("received", received);

        return hashMap;
    }

    public Boolean checkPassword (String inputPassword, String userPassword){
        return passwordEncoder.matches(inputPassword, userPassword);
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

    public void isExistingUserByEmail (String email){
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
