package com.favor.favor.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusCode {
    OK(HttpStatus.OK, "OK"),

    CREATED(HttpStatus.CREATED, "CREATED"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD REQUEST"),
    EMAIL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "EMAIL BAD REQUEST"),
    PASSWORD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PASSWORD BAD REQUEST"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN"),

    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"),

    EMAIL_CONFLICT(HttpStatus.CONFLICT, "EMAIL CONFLICT"),
    ID_CONFLICT(HttpStatus.CONFLICT, "ID CONFLICT"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");

    private final HttpStatus httpStatus;
    private final String message;
}
