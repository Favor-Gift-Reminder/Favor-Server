package com.favor.favor.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    //400 BAD_REQUEST : 잘못된 요청
    FIELD_REQUIRED(BAD_REQUEST, "입력은 필수입니다."),
    PASSWORD_INVALID(BAD_REQUEST, "두 비밀번호가 일치하지 않습니다."),

    // 형식
    EMAIL_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 이메일이 아닙니다."),
    PASSWORD_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 비밀번호가 아닙니다."),
    ID_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 아이디가 아닙니다."),

    // 사이즈
    ID_LENGTH_INVALID(BAD_REQUEST, "아이디는 2~8자만 가능합니다."),
    PASSWORD_LENGTH_INVALID(BAD_REQUEST, "비밀번호는 8~15자만 가능합니다."),

    ILLEGAL_ARGUMENT_ADMIN(BAD_REQUEST, "관리자는 해당 요청의 설정 대상이 될 수 없습니다."),
    ILLEGAL_ARGUMENT_SELF(BAD_REQUEST, "본인은 해당 요청의 설정 대상이 될 수 없습니다."),
    ILLEGAL_ARGUMENT_FRIEND(BAD_REQUEST, "친구는 해당 요청의 설정 대상이 될 수 없습니다."),

    /**
     * 401 UNAUTHORIZED : 인증되지 않은 사용자
     */
    INVALID_CODE(UNAUTHORIZED, "올바르지 않은 코드입니다."),
    EXPIRED_CODE(UNAUTHORIZED, "이메일 인증 유효시간이 초과되었습니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "로그인이 필요합니다."),
    UNAUTHORIZED_PASSWORD(UNAUTHORIZED, "올바르지 않은 비밀번호입니다."),
    UNAUTHORIZED_EMAIL(UNAUTHORIZED, "인증이 되지 않은 이메일입니다."),

    UNAUTHORIZED_POST(UNAUTHORIZED, "게시글 수정 및 삭제 권한이 없습니다"),
    UNAUTHORIZED_COMMENT(UNAUTHORIZED, "댓글 수정 및 삭제 권한이 없습니다."),
    /**
     * 403 FORBIDDEN : 권한이 없는 사용자
     */
    FORBIDDEN_AUTHORIZATION(FORBIDDEN, "접근 권한이 없습니다."),

    /**
     * 404 NOT_FOUND : Resource 를 찾을 수 없음
     */
    EMAIL_NOT_FOUND(NOT_FOUND, "등록된 이메일이 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "등록된 회원이 없습니다."),
    FRIEND_NOT_FOUND(NOT_FOUND, "등록된 친구가 없습니다."),
    FRIEND_USER_NOT_FOUND(NOT_FOUND, "친구로 등록하려는 회원이 없습니다."),
    GIFT_NOT_FOUND(NOT_FOUND, "등록한 선물이 없습니다."),
    REMINDER_NOT_FOUND(NOT_FOUND, "등록한 리마인더가 없습니다."),

    /**
     * 405 METHOD_NOT_ALLOWED : 대상 리소스가 해당 메서드를 지원하지 않음
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메서드입니다."),

    /**
     * 409 CONFLICT : Resource 의 현재 상태와 충돌. 중복된 데이터
     */
    DUPLICATE_EMAIL(CONFLICT, "이미 등록된 이메일입니다."),
    DUPLICATE_USER(CONFLICT, "이미 등록된 회원입니다."),
    DUPLICATE_ID(CONFLICT, "이미 등록된 아이디입니다."),
    DUPLICATE_FRIEND(CONFLICT, "이미 등록된 친구입니다."),

    /**
     * 413 PAYLOAD_TOO_LARGE
     */
    FILE_SIZE_EXCEED(PAYLOAD_TOO_LARGE, "파일 용량이 초과되었습니다"),
    FILE_COUNT_EXCEED(PAYLOAD_TOO_LARGE, "파일 개수가 초과되었습니다"),

    /**
     * 415 UNSUPPORTED_MEDIA_TYPE
     */
    FILE_TYPE_UNSUPPORTED(UNSUPPORTED_MEDIA_TYPE, "파일 형식은 '.jpg', '.jpeg', '.png' 만 가능합니다."),

    /**
     * 500 SERVER_ERROR : 서버 에러
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
