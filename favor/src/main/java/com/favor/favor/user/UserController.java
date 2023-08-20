package com.favor.favor.user;


import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.common.enums.GiftCategory;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.gift.GiftSimpleDto;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.reminder.ReminderSimpleDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserPhotoService userPhotoService;

    @ApiOperation(value = "회원가입")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_REGISTERED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_EMAIL"),
            @ApiResponse(code = 404,
                    message = "EMAIL_NOT_FOUND"),
            @ApiResponse(code = 409,
                    message = "DUPLICATE_EMAIL"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<DefaultResponseDto<Object>> signUp(
            @RequestBody @Valid SignDto signDto
    ) {

        userService.isExistingEmail(signDto.getEmail());

        User user = userService.signUp(signDto);
        UserResponseDto dto = userService.returnUserDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("회원가입 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("프로필 생성")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "PROFILE_UPDATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_EMAIL"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 409,
                    message = "DUPLICATE_ID"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> createProfile(
            @RequestBody @Valid ProfileDto profileDto,
            @AuthenticationPrincipal User loginUser) {

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserId(profileDto.getUserId());

        User user = userService.createProfile(profileDto, userNo);
        UserResponseDto dto = userService.returnUserDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("PROFILE_UPDATED")
                        .responseMessage("프로필 생성 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation(value = "로그인")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_SIGNED_IN",
                    response = SignInResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_EMAIL"),
            @ApiResponse(code = 404,
                    message = "EMAIL_NOT_FOUND"),
            @ApiResponse(code = 409,
                    message = "DUPLICATE_EMAIL"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-in")
    public ResponseEntity<DefaultResponseDto<Object>> signIn(
            @RequestBody @Valid SignDto signDto
    ) {

        SignInResponseDto dto = userService.signIn(signDto);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("LOG_IN_SUCCESS")
                        .responseMessage("로그인 완료")
                        .data(dto)
                        .build());
    }


    @ApiOperation("회원 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> readUser(
            @AuthenticationPrincipal User loginUser
    ){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        User user = userService.findUserByUserNo(userNo);

        UserResponseDto dto = userService.returnUserDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_FOUND")
                        .responseMessage("회원 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("회원 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_UPDATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            @AuthenticationPrincipal User loginUser,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto
    ){

        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);
        User user = userService.findUserByUserNo(userNo);

        userService.updateUser(user, userUpdateRequestDto);
        UserResponseDto dto = userService.returnUserDto(user);


        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("회원 수정 완료")
                        .data(dto)
                        .build());

    }

    @ApiOperation("회원 탈퇴")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public ResponseEntity<DefaultResponseDto<Object>> deleteUser(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        User user = userService.findUserByUserNo(userNo);
        UserResponseDto dto = userService.returnUserDto(user);

        userPhotoService.deleteUserProfilePhoto(userNo);
        userPhotoService.deleteUserBackgroundPhoto(userNo);

        userService.deleteUser(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_DELETED")
                        .responseMessage("회원 탈퇴 완료 (임시)")
                        .data(dto)
                        .build());
    }

    @ApiOperation("비밀번호 변경")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "PASSWORD_UPDATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / EMAIL_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/password")
    public ResponseEntity<DefaultResponseDto<Object>> updatePassword(
            @RequestBody @Valid UserUpdatePasswordRequestDto passwordDto){

        userService.validateExistingEmail(passwordDto.getEmail());

        User user = userService.updatePassword(passwordDto.getEmail(), passwordDto.getPassword1());
        UserResponseDto dto = userService.returnUserDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("PASSWORD_UPDATED")
                        .responseMessage("비밀번호 변경 완료")
                        .data(dto)
                        .build());
    }


    @ApiOperation("회원의 리마인더 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDERS_FOUND",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reminders")
    public ResponseEntity<DefaultResponseDto<Object>> readReminderList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<ReminderSimpleDto> reminders = userService.readReminderList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDERS_FOUND")
                        .responseMessage("회원의 리마인더 전체 조회 완료")
                        .data(reminders)
                        .build());
    }
    @ApiOperation("회원의 리마인더 필터 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDER_FOUND_BY_FILTER",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reminders/{year}/{month}")
    public ResponseEntity<DefaultResponseDto<Object>> readReminderListByFMonthAndYear(
            @AuthenticationPrincipal User loginUser,
            @PathVariable int year,
            @PathVariable int month){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<ReminderSimpleDto> dto = userService.readReminderListByFMonthAndYear(userNo, year, month);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_FOUND_BY_FILTER")
                        .responseMessage("리마인더 필터 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("회원의 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> gifts = userService.readGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_FOUND")
                        .responseMessage("회원의 선물 전체 조회 완료")
                        .data(gifts)
                        .build());
    }

    @ApiOperation("회원의 친구 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIENDS_FOUND",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/friends")
    public ResponseEntity<DefaultResponseDto<Object>> readFriendList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<FriendSimpleDto> friends = userService.readFriendList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FRIENDS_FOUND")
                        .responseMessage("회원의 친구 전체 조회 완료")
                        .data(friends)
                        .build());
    }

    @ApiOperation("회원의 기념일 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARY_FOUND",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/anniversaries")
    public ResponseEntity<DefaultResponseDto<Object>> readAnniversaryList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<AnniversaryResponseDto> friends = userService.readAnniversaryList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("ANNIVERSARY_FOUND")
                        .responseMessage("회원의 기념일 전체 조회 완료")
                        .data(friends)
                        .build());
    }


    @ApiOperation("전체 회원 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USERS_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<UserResponseDto> dto = userService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USERS_FOUND")
                        .responseMessage("전체 회원 조회 완료")
                        .data(dto)
                        .build());
    }


    @ApiOperation("이름으로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_NAME_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts-by-name/{giftName}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByName (
            @AuthenticationPrincipal User loginUser,
            @PathVariable("giftName") String giftName){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByName(userNo, giftName);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_BY_NAME_FOUND")
                        .responseMessage("이름으로 회원 선물 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("카테고리로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_CATEGORY_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts-by-category/{category}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByCategory(
            @AuthenticationPrincipal User loginUser,
            @PathVariable("category") GiftCategory giftCategory){

        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByCategory(userNo, giftCategory);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_BY_CATEGORY_FOUND")
                        .responseMessage("카테고리로 회원 선물 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("감정으로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_EMOTION_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts-by-emotion/{emotion}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByEmotion(
            @AuthenticationPrincipal User loginUser,
            @PathVariable("emotion") Emotion emotion){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByEmotion(userNo, emotion);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_BY_CATEGORY_FOUND")
                        .responseMessage("감정으로 회원 선물 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("아이디로 회원 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_BY_ID_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public ResponseEntity<DefaultResponseDto<Object>> readUserByUserId(
            @PathVariable("userId") String userId){

        User user = userService.findUserByUserId(userId);
        UserResponseDto dto = userService.returnUserDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_BY_ID_FOUND")
                        .responseMessage("아이디로 회원 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("유저가 준 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts-given")
    public ResponseEntity<DefaultResponseDto<Object>> readGivenGiftList(
            @AuthenticationPrincipal User loginUser){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);
        List<GiftSimpleDto> dto = userService.findGivenGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_FOUND")
                        .responseMessage("유저가 준 선물 전체 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("유저가 받은 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/gifts-received")
    public ResponseEntity<DefaultResponseDto<Object>> readReceivedGiftList(
            @AuthenticationPrincipal User loginUser){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);
        List<GiftSimpleDto> dto = userService.findReceivedGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_FOUND")
                        .responseMessage("유저가 받은 선물 전체 조회 완료")
                        .data(dto)
                        .build());
    }

}
