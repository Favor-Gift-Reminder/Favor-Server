package com.favor.favor.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    //400 BAD_REQUEST : 잘못된 요청
    FIELD_REQUIRED(BAD_REQUEST, "입력은 필수입니다"),

    // 형식
    EMAIL_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 이메일이 아닙니다."),
    PASSWORD_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 비밀번호가 아닙니다."),
    ID_CHARACTER_INVALID(BAD_REQUEST, "올바른 형식의 아이디가 아닙니다."),

    // 사이즈
    ID_LENGTH_INVALID(BAD_REQUEST, "아이디는 2~8자만 가능합니다."),
    PASSWORD_LENGTH_INVALID(BAD_REQUEST, "비밀번호는 8~15자만 가능합니다."),

    ILLEGAL_ARGUMENT_ADMIN(BAD_REQUEST, "관리자는 해당 요청의 설정 대상이 될 수 없습니다."),
    ILLEGAL_ARGUMENT_SELF(BAD_REQUEST, "본인은 해당 요청의 설정 대상이 될 수 없습니다."),

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

    DELETED_POST(NOT_FOUND, "삭제된 게시글입니다."),
    DELETED_COMMENT(NOT_FOUND, "삭제된 댓글입니다."),

    /**
     * 405 METHOD_NOT_ALLOWED : 대상 리소스가 해당 메서드를 지원하지 않음
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메서드입니다."),

    /**
     * 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
     */

    DUPLICATE_EMAIL(CONFLICT, "이미 등록된 이메일입니다."),
    DUPLICATE_USER(CONFLICT, "이미 등록된 회원입니다."),
    DUPLICATE_ID(CONFLICT, "이미 등록된 아이디입니다."),

    CANNOT_UPDATE_ID(CONFLICT, "아이디는 30일마다 변경할 수 있습니다."),
    CANNOT_LIKED_SELF(CONFLICT, "본인의 글에 공감할 수 없습니다."),
    USER_CANNOT_SELFREPORT(CONFLICT, "본인은 신고할 수 없습니다."),
    POST_CANNOT_SELFREPORT(CONFLICT, "본인의 게시글은 신고할 수 없습니다."),
    COMMENT_CANNOT_SELFREPORT(CONFLICT, "본인의 댓글은 신고할 수 없습니다."),

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
