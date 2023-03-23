# Favor-Server (수정 예정)
- Favor(페이버) 서버 리포지토리

</br>

## ERD
- ERD Cloud
- https://www.notion.so/Favor-App-b1016ef31bbd486fb469063e3f9650c6
![20230116152805](https://user-images.githubusercontent.com/114793764/212612357-b2a88b1c-af2a-4cd3-8798-c2465b67b692.png)





  


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
  
![20230116152745](https://user-images.githubusercontent.com/114793764/212612371-19cb0ede-0ef5-4d61-8090-83bd73459928.png)
![20230116152753](https://user-images.githubusercontent.com/114793764/212612377-67cc699d-7bcb-48a4-9478-b4706af91b2a.png)


