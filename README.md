# Guest Book 2.0
22년도에 배포했던 [게스트북](https://github.com/EunsilSon/2022-guestbook)의 2.0 버전입니다.

이전에 개발한 토이 프로젝트를 업그레이드하며 제가 가진 **기술의 능력치를 높이기** 위한 프로젝트입니다.  
앞으로 변경된 사항들이 READ.ME에 기재되고, 개발 과정과 트러블슈팅 관련 내용은 저의 🔗[기술 블로그](https://velog.io/@eunsilson/posts)에 업로드됩니다.

<br>

# 업그레이드 목록
### 개발 환경
  1. [Spring Boot 2.7.1 → 3.2.5 마이그레이션](https://velog.io/@eunsilson/Spring-Boot-3-2-5-%EB%A7%88%EC%9D%B4%EA%B7%B8%EB%A0%88%EC%9D%B4%EC%85%98)

<br>

### 로직
  1. 회원 가입 시 **데이터베이스 레벨에서 중복 값 체크**
      - 기존에 DB에서 데이터를 가져와 비교하는 로직 제거
      - 중복 값을 허용하지 않는 필드의 멤버 변수에 `@UniqueConstraint` 주입
      - 보안성 증가
  2. **NullPointerException 발생 가능성 감소 및 실행 속도 감소**
        - 기존에 find 메서드로 객체를 찾아 null과 비교하는 로직을 제거하고 `existsBy` 메서드를 사용해 가독성 증가
        - 실행 시간 약 60% 감소 (1225ms → 487ms)