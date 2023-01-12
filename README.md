# Favor-Server
- Favor(페이버) 서버 리포지토리

</br>

## ERD
- ERD Cloud
- https://www.erdcloud.com/d/fTpq4mpS53n96ga7E  
![20230112195445](https://user-images.githubusercontent.com/114793764/212048642-eca722a6-cdeb-44a2-98ee-643e83460bb3.png)




  


- 실제로 사진을 DB에 저장 할 경우
  - 비효율적, 병목현상 가능
  - 따라서 서버의 특정 위치에 사진 저장하고 DB에는 사진 정보만을 저장
- 친구 추가 기능 고민중

</br>

## 요구사항 정리
- 카카오 로그인 API 사용 (고려)
  - https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api  
  - 토큰 사용방식  
- 친구의 경우 회원과 비회원으로 나누어 정리
- 이메일 인증 서비스 구현 (고려)
  - 전화번호 인증의 경우 유료 서비스

</br>

## API 명세서
- https://www.notion.so/5bca3cd4be2343b9a006aff1daaa2007?v=6ca569ec7a5b416ab064a22ca5abf6a2  
  
![20230112195409](https://user-images.githubusercontent.com/114793764/212048704-d65f2d0f-5e9b-4225-a01c-e80dcaa50ccf.png)
![20230112195416](https://user-images.githubusercontent.com/114793764/212048712-4e2cb5eb-7b7d-4eb2-9ce4-4b0ecbbdc0cc.png)




