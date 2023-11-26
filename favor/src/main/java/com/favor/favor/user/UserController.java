package com.favor.favor.user;


import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.common.enums.GiftCategory;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.gift.GiftSimpleDto;
import com.favor.favor.reminder.ReminderSimpleDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.favor.favor.common.DefaultResponseDto.from;
import static com.favor.favor.common.DefaultResponseDto.from;

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
    @PostMapping("/sign-up")
    public ResponseEntity<DefaultResponseDto<Object>> signUp(
            @RequestBody @Valid SignDto signDto
    ) {

        userService.isExistingEmail(signDto.getEmail());

        UserResponseDto dto = userService.signUp(signDto);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.from("USER_REGISTERED", "회원가입 완료", dto));
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
    @PatchMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> createProfile(
            @RequestBody @Valid ProfileDto profileDto,
            @AuthenticationPrincipal User loginUser) {

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserId(profileDto.getUserId());

        UserResponseDto dto = userService.createProfile(profileDto, userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("PROFILE_UPDATED", "프로필 생성 완료", dto));
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
    @PostMapping("/sign-in")
    public ResponseEntity<DefaultResponseDto<Object>> signIn(
            @RequestBody @Valid SignDto signDto
    ) {

        SignInResponseDto dto = userService.signIn(signDto);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.from("LOG_IN_SUCCESS", "로그인 완료", dto));
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
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> readUser(
            @AuthenticationPrincipal User loginUser
    ){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        UserResponseDto dto = userService.readUserInfo(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("USER_FOUND", "회원 조회 완료", dto));
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
    @PatchMapping
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            @AuthenticationPrincipal User loginUser,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto
    ){

        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);
        UserResponseDto dto = userService.updateUser(userNo, userUpdateRequestDto);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("USER_UPDATED", "회원 수정 완료", dto));

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
    @DeleteMapping
    public ResponseEntity<DefaultResponseDto<Object>> deleteUser(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        userPhotoService.deleteUserProfilePhoto(userNo);
        userPhotoService.deleteUserBackgroundPhoto(userNo);

        userService.deleteUser(userNo);

        return ResponseEntity.status(200)
                .body(from("USER_DELETED", "회원 탈퇴 완료"));
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
    @PatchMapping("/password")
    public ResponseEntity<DefaultResponseDto<Object>> updatePassword(
            @RequestBody @Valid UserUpdatePasswordRequestDto passwordDto){

        userService.validateExistingEmail(passwordDto.getEmail());

        UserResponseDto dto = userService.updatePassword(passwordDto.getEmail(), passwordDto.getPassword());

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("PASSWORD_UPDATED", "비밀번호 변경 완료", dto));
    }


    @ApiOperation("회원의 리마인더 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDERS_FOUND",
                    response = ReminderSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/reminders")
    public ResponseEntity<DefaultResponseDto<Object>> readReminderList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<ReminderSimpleDto> dto = userService.readReminderList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("REMINDERS_FOUND", "회원의 리마인더 전체 조회 완료", dto));
    }
    @ApiOperation("회원의 리마인더 필터 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDER_FOUND_BY_FILTER",
                    response = ReminderSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/reminders/{year}/{month}")
    public ResponseEntity<DefaultResponseDto<Object>> readReminderListByFMonthAndYear(
            @AuthenticationPrincipal User loginUser,
            @PathVariable int year,
            @PathVariable int month){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<ReminderSimpleDto> dto = userService.readReminderListByFMonthAndYear(userNo, year, month);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("REMINDER_FOUND_BY_FILTER", "회원가입 완료", dto));
    }

    @ApiOperation("회원의 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto = userService.readGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("GIFTS_FOUND", "회원의 선물 전체 조회 완료", dto));
    }

    @ApiOperation("회원의 친구 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIENDS_FOUND",
                    response = FriendSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/friends")
    public ResponseEntity<DefaultResponseDto<Object>> readFriendList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<FriendSimpleDto> dto = userService.readFriendList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("FRIENDS_FOUND", "회원의 친구 전체 조회 완료", dto));
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
    @GetMapping("/anniversaries")
    public ResponseEntity<DefaultResponseDto<Object>> readAnniversaryList(
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<AnniversaryResponseDto> dto = userService.readAnniversaryList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("ANNIVERSARY_FOUND", "회원의 기념일 전체 조회 완료", dto));
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
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<UserResponseDto> dto = userService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("USERS_FOUND", "전체 회원 조회 완료", dto));
    }


    @ApiOperation("이름으로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_NAME_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts-by-name/{giftName}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByName (
            @AuthenticationPrincipal User loginUser,
            @PathVariable("giftName") String giftName){

        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByName(userNo, giftName);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("GIFTS_BY_NAME_FOUND", "이름으로 회원 선물 조회 완료", dto));
    }

    @ApiOperation("카테고리로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_CATEGORY_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts-by-category/{category}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByCategory(
            @AuthenticationPrincipal User loginUser,
            @PathVariable("category") GiftCategory giftCategory){

        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByCategory(userNo, giftCategory);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("GIFTS_BY_CATEGORY_FOUND", "카테고리로 회원 선물 조회 완료", dto));
    }

    @ApiOperation("감정으로 회원 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_BY_EMOTION_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts-by-emotion/{emotion}")
    public ResponseEntity<DefaultResponseDto<Object>> readGiftListByEmotion(
            @AuthenticationPrincipal User loginUser,
            @PathVariable("emotion") Emotion emotion){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);

        List<GiftSimpleDto> dto =  userService.readGiftListByEmotion(userNo, emotion);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("GIFTS_BY_CATEGORY_FOUND", "감정으로 회원 선물 조회 완료", dto));
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
    @GetMapping("/{userId}")
    public ResponseEntity<DefaultResponseDto<Object>> readUserByUserId(
            @PathVariable("userId") String userId){

        User user = userService.findUserByUserId(userId);
        UserResponseDto dto = userService.readUserInfo(user.getUserNo());

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("USER_BY_ID_FOUND", "아이디로 회원 조회 완료", dto));
    }

    @ApiOperation("유저가 준 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIVEN_GIFTS_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts-given")
    public ResponseEntity<DefaultResponseDto<Object>> readGivenGiftList(
            @AuthenticationPrincipal User loginUser){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);
        List<GiftSimpleDto> dto = userService.readGivenGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("GIVEN_GIFTS_FOUND", "유저가 준 선물 전체 조회 완료", dto));
    }

    @ApiOperation("유저가 받은 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "RECEIVED_GIFTS_FOUND",
                    response = GiftSimpleDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/gifts-received")
    public ResponseEntity<DefaultResponseDto<Object>> readReceivedGiftList(
            @AuthenticationPrincipal User loginUser){
        Long userNo = loginUser.getUserNo();

        userService.isExistingUserNo(userNo);
        List<GiftSimpleDto> dto = userService.readReceivedGiftList(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("RECEIVED_GIFTS_FOUND", "유저가 받은 선물 전체 조회 완료", dto));
    }

}
