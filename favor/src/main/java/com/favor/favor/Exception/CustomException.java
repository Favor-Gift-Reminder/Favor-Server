package com.favor.favor.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final Exception exception;
    private final ExceptionCode exceptionCode;
}
