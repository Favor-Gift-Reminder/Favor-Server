package com.favor.favor.exception;

import org.springframework.security.web.access.AccessDeniedHandler;

import static com.favor.favor.exception.ExceptionCode.FORBIDDEN_AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static DefaultExceptionResponseDto errorResponse =
            new DefaultExceptionResponseDto(FORBIDDEN.name(),
                    FORBIDDEN_AUTHORIZATION.getMessage());

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException exception) throws IOException {

        // [handle] 접근이 막혔을 경우 경로 리다이렉트
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(FORBIDDEN.value());
        try (OutputStream os = httpServletResponse.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, errorResponse);
            os.flush();
        }
    }
}
