package com.favor.favor.user;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.auth.JwtTokenProvider;
import com.favor.favor.common.enums.GiftCategory;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.gift.*;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderRepository;
import com.favor.favor.reminder.ReminderSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;
    private final FriendRepository friendRepository;
    private final ReminderRepository reminderRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto signUp(SignDto signDto) {

        User user = User.builder()
                .name("Favor00")
                .userId("Favor00")
                .email(signDto.getEmail())
                .password(passwordEncoder.encode(signDto.getPassword()))
                .role(Role.USER)
                .build();
        save(user);

        return user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo()));
    }

    @Transactional
    public UserResponseDto createProfile(ProfileDto profileDto, Long userNo) {
        User user = findUserByUserNo(userNo);

        user.setName(profileDto.getName());
        user.setUserId(profileDto.getUserId());
        save(user);

        return user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo()));
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

        } catch (RuntimeException e){
            throw new CustomException(e, PASSWORD_NOT_FOUND);
        }
    }

    @Transactional
    public UserResponseDto updateUser(Long userNo, UserUpdateRequestDto userUpdateRequestDto){
        User user = findUserByUserNo(userNo);
        user.setName(userUpdateRequestDto.getName());
        user.setUserId(userUpdateRequestDto.getUserId());
        user.setFavorList(userUpdateRequestDto.getFavorList());
        save(user);

        return user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo()));
    }

    @Transactional
    public void deleteUser(Long userNo) {
        friendRepository.deleteFriendsByFriendUserNo(userNo);
        userRepository.deleteByUserNo(userNo);
    }

    @Transactional
    public UserResponseDto updatePassword(String email, String password){
        User user = findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        save(user);

        return user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo()));
    }

    public UserResponseDto readUserInfo(Long userNo) {
        User user = findUserByUserNo(userNo);
        return user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo()));
    }

    @Transactional
    public List<UserResponseDto> readAll(){
        return userRepository.findAll().stream()
                .map(user -> user.toDto(user, readReminderList(user.getUserNo()), readFriendList(user.getUserNo()), readFavorList(user.getUserNo()), readAnniversaryList(user.getUserNo()), getGiftInfo(user.getUserNo())))
                .collect(Collectors.toList());
    }

    public List<GiftSimpleDto> readGiftList(Long userNo) {
        User user = findUserByUserNo(userNo);

        return giftRepository.findGiftsByUser(user).stream()
                .map(GiftSimpleDto::new).collect(Collectors.toList());
    }

    public List<GiftSimpleDto> readGiftListByName(Long userNo, String giftName){
        User user = findUserByUserNo(userNo);

        return giftRepository.findGiftsByUserAndGiftNameContains(user, giftName).stream()
                .map(GiftSimpleDto::new).collect(Collectors.toList());
    }

    public List<GiftSimpleDto> readGiftListByCategory(Long userNo, GiftCategory giftCategory){
        User user = findUserByUserNo(userNo);
        Integer categoryNo = giftCategory.getType();

        return giftRepository.findGiftsByUserAndCategory(user, categoryNo).stream()
                .map(GiftSimpleDto::new)
                .collect(Collectors.toList());
    }

    public List<GiftSimpleDto> readGiftListByEmotion(Long userNo, Emotion emotion){
        User user = findUserByUserNo(userNo);
        Integer emotionNo = emotion.getType();

        return giftRepository.findGiftsByUserAndEmotion(user, emotionNo).stream()
                .map(GiftSimpleDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(User user){
        try{
            userRepository.save(user);
        } catch(RuntimeException e){
            //500
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    //VALIDATE
    public void validateExistingEmail (String email){
        Boolean isExistingEmail;
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
        User user;
        try{
            user = userRepository.findByUserNo(userNo).orElseThrow(
                    RuntimeException::new
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    public User findUserByUserId(String userId){
        User user;
        try{
            user = userRepository.findByUserId(userId).orElseThrow(
                    RuntimeException::new
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    public User findUserByEmail(String email){
        User user;
        try{
            user = userRepository.findByEmail(email).orElseThrow(
                    RuntimeException::new
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    public List<ReminderSimpleDto> readReminderListByFMonthAndYear(Long userNo, int year, int month){
        try{
            LocalDate.of(year, month, 1);
        } catch(Exception e){
            throw new CustomException(e, DATE_INVALID);
        }

        List<Reminder> reminders = readReminderListByMonthAndYear(year, month);
        return reminders.stream()
                .filter(reminder -> reminder.getUser().getUserNo().equals(userNo))
                .map(reminder -> {
                    Friend friend = reminder.getFriend();
                    FriendSimpleDto friendDto = null;
                    if (friend != null) {
                        User friendUser = findUserByUserNo(friend.getFriendUserNo());
                        UserPhoto photo = friendUser != null ? friendUser.getUserProfilePhoto() : null;
                        friendDto = new FriendSimpleDto(friend, friendUser, photo);
                    }
                    return new ReminderSimpleDto(reminder, friendDto);
                })
                .collect(Collectors.toList());
    }


    public List<Reminder> readReminderListByMonthAndYear(int year, int month){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        return reminderRepository.findAllByReminderDateBetween(start, end);
    }

    public List<GiftSimpleDto> readGivenGiftList(Long userNo){
        User user = findUserByUserNo(userNo);
        return giftRepository.findGiftsByUser(user).stream()
                .map(GiftSimpleDto::new)
                .collect(Collectors.toList());
    }

    public List<GiftSimpleDto> readReceivedGiftList(Long userNo){
        User user = findUserByUserNo(userNo);
        return giftRepository.findGiftsByUser(user).stream()
                .map(GiftSimpleDto::new)
                .collect(Collectors.toList());
    }

    public List<ReminderSimpleDto> readReminderList(Long userNo) {
        User user = findUserByUserNo(userNo);
        return user.getReminderList().stream()
                .map(reminder -> {
                    Friend friend = reminder.getFriend();
                    FriendSimpleDto friendDto = null;
                    if (friend != null) {
                        User friendUser = findUserByUserNo(friend.getFriendUserNo());
                        UserPhoto photo = friendUser != null ? friendUser.getUserProfilePhoto() : null;
                        friendDto = new FriendSimpleDto(friend, friendUser, photo);
                    }
                    return new ReminderSimpleDto(reminder, friendDto);
                }).collect(Collectors.toList());
    }


    public List<FriendSimpleDto> readFriendList(Long userNo) {
        User user = findUserByUserNo(userNo);
        return user.getFriendList().stream()
                .map(friend -> new FriendSimpleDto(friend, friend.getUser(), friend.getUser().getUserProfilePhoto())).collect(Collectors.toList());
    }

    public List<AnniversaryResponseDto> readAnniversaryList(Long userNo) {
        User user = findUserByUserNo(userNo);
        return user.getAnniversaryList().stream()
                .map(AnniversaryResponseDto::new).collect(Collectors.toList());
    }

    public List<Favor> readFavorList(Long userNo) {
        User user = findUserByUserNo(userNo);
        return user.getFavorList().stream()
                .map(Favor::valueOf).collect(Collectors.toList());
    }

    public HashMap<String, Integer> getGiftInfo(Long userNo){
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

    //IS_EXISTING
    public void isExistingUserId (String id){
        Boolean isExistingId;
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
        Boolean isExistingEmail;
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
        Boolean isExistingEmail;
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
        Boolean isExistingUser;
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
