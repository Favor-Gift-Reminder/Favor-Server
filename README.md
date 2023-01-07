# Favor-Server
- Favor(페이버) 서버 리포지토리

</br>

## ERD
- ERD Cloud
- https://www.erdcloud.com/d/fTpq4mpS53n96ga7E  

  ![20230107102727](https://user-images.githubusercontent.com/114793764/211125022-d4ee1279-2f4c-4a6b-83ef-a973d8685683.png)


  


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

## API 명세서 (진행중)
- https://www.notion.so/5bca3cd4be2343b9a006aff1daaa2007?v=6ca569ec7a5b416ab064a22ca5abf6a2  
  
![20230106160309](https://user-images.githubusercontent.com/114793764/210948684-9432e5a8-3b4c-460b-9b1a-399194b47032.png)  



