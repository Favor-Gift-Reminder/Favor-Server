package com.favor.favor.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtTokenizer {
    // plain 형태의 secret key Byte[]를 Base64 형식의 문자열로 인코딩
    public String encoderBase64SecretKey(String secretKey){
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    // 인증된 사용자에게 JWT 최초로 발급
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncoderSecretKey){
        //Base64 형식 Secret Key 문자열을 이용해
        // Key(java.security.Key) 객체를 얻는다
        Key key = getKeyFromBase64EncodeKey(base64EncoderSecretKey);

        return Jwts.builder()
                // JWT 에 포함시킬 Custom Claims 추가
                .setClaims(claims)
                // setSubject() JWT에 대한 제목 추가
                .setSubject(subject)
                //setIssuedAt() JWT 발행 일자를 설정, 파라미터 타입은 java.util.Date 타입이다.
                .setIssuedAt(Calendar.getInstance().getTime())
                //setExpiration() JWT의 만료일시 지정. 파라미터는 역시 Date 타입
                .setExpiration(expiration)
                //signWith() 에 서명을 위한 Key 객체를 설정
                .signWith(key)
                //compact() 를 통해 JWT를 생성하고 직렬화한다.
                .compact();
    }

    // Access Token이 만료되었을 경우,
    // 새로 생성할 수 있게 해주는 Refresh Token을 생성하는 메서드
    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodeKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    //JWT의 서명에 사용할 Secret Key를 생성
    private Key getKeyFromBase64EncodeKey(String base64EncodedSecretKey){
        //Base64 형식으로 인코딩 된 시크릿키를 디코딩 한 후, byte[]를 반환
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        //key byte array 를 기반으로 적절한 HMAC 알고리즘을 적용한 Key 객체를 생성
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    /*JwtTokenizer 클래스에 JWT 검증을 위한 메서드 추가
    *
    * jjwt에서는 JWT를 생성할 때
    * 서명에 사용된 시크릿키를 이용해 내부적으로 Signature를 검증 한 후,
    * 검증에 성공하면 JWT를 파싱해서 Claims를 얻을 수 있다.
    *
    * 파라미터로 사용한 jws는 Signature가 포함된 JWT라는 의미
    * */
    public void verifySignature(String jws, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodeKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                //서명에 사용된 시크릿키를 설정
                .setSigningKey(key)
                .build()
                //JWT를 파싱해서 Claims를 얻는다
                .parseClaimsJws(jws);
    }
}
