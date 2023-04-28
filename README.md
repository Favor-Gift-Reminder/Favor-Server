# Favor-Server (수정 예정)
- Favor(페이버) 서버 리포지토리

</br>

## ERD
- ERD Cloud
- https://www.erdcloud.com/d/fTpq4mpS53n96ga7E
![20230428164330](https://user-images.githubusercontent.com/114793764/235086375-d543ce11-7fa3-4206-a570-6eda62f20baf.png)






  


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
![20230428110520](https://user-images.githubusercontent.com/114793764/235037004-add3a23e-e6b2-4de4-bd2b-3bd3bdab359c.png)
![20230428110526](https://user-images.githubusercontent.com/114793764/235037009-953b67fc-eb40-450b-9271-c00ec038d265.png)
![20230428110531](https://user-images.githubusercontent.com/114793764/235037012-fb8f7eb1-c7f8-4159-9a04-bb1de6391c2b.png)
![20230428110536](https://user-images.githubusercontent.com/114793764/235037016-8003f567-fe4e-45f4-9f43-8649ec2e09a0.png)



