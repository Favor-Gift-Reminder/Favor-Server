package com.favor.favor.exception;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.favor.favor.exception.ExceptionCode.UNAUTHORIZED_USER;
import static org.springframework.http.HttpStatus.*;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static CustomException errorResponse =
            new CustomException(UNAUTHORIZED,
                    UNAUTHORIZED_USER.get);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException, ServletException {

        // [commence] 인증 실패로 response.sendError 발생
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try (OutputStream os = response.getOutputStream()) {
            objectMapper.writeValue(os, errorResponse);
            os.flush();
        }
    }