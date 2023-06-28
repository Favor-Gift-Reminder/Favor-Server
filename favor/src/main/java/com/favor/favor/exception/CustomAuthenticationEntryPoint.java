package com.favor.favor.exception;

import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        // [commence] 인증 실패로 response.sendError 발생

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(401);

        JSONObject responseJson = new JSONObject();
        responseJson.put("responseCode", "AUTHENTICATION_FAILED");
        responseJson.put("responseMessage", "인증에 실패하였습니다.");

        response.getWriter().print(responseJson);
    }
}
