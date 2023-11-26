package com.favor.favor.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ApiModel(value = "기본 응답")
public class DefaultResponseDto <T> {

    @ApiModelProperty(position = 1, value = "응답 코드", example = "RESPONSE_CODE")
    private String responseCode;
    @ApiModelProperty(position = 2, value = "응답 메세지", example = "응답 메세지")
    private String responseMessage;
    @ApiModelProperty(position = 3, value = "응답 데이터", example = "응답 데이터")
    private T data;


    @Builder
    private DefaultResponseDto(String responseCode, String responseMessage, T data){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public static <T> DefaultResponseDto<T> from(String responseCode, String responseMessage){
        return DefaultResponseDto.<T>builder()
                .responseCode(responseCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static <T> DefaultResponseDto<T> from(String responseCode, String responseMessage, T data){
        return DefaultResponseDto.<T>builder()
                .responseCode(responseCode)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}
