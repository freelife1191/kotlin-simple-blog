# Kotlin Simple Blog Project

[스프링부트 + 코틀린 - 블로그 프로젝트](https://www.youtube.com/playlist?list=PLiLLi47PCMPjdezfGUnayz5PwEfwAQCBi)

- [X] blog 1. 환경설정
- [X] blog 2. JPA Entity 생성
- [X] blog 3. Entity 연관관계 설정 및 더미데이터 생성
- [X] blog 4. Logback 설정
- [X] blog 5. dto entity 맵핑 1
- [X] blog 6. dto entity 맵핑 2
- [X] blog 7. kotlin-jdsl 살펴보기 + pagenation
- [X] blog 8. dto validation
- [X] blog 9. Custom Exception Handling
- [X] blog 10. AOP Logging Aspect, Fetch Join
- [X] blog 11. Filter를 통한 인증처리
- [X] blog 12. 계획
- [X] blog 13. 스프링 시큐리티 적용 1
- [X] blog 14. 스프링 시큐리티 적용 2 jwt 발급
- [X] blog 15. 스프링 시큐리티 적용 3 기본인증필터
- [X] blog 16. 스프링 시큐리티 적용 4 실패/성공 핸들링
- [X] blog 17. 스프링 시큐리티 적용 5 url 기반 인가처리
- [X] blog 18. JWT Manager 수정
- [X] blog 19. ObjectMapper config
- [X] blog 20. 스프링 시큐리티 적용 6 메서드 호출 보안
- [X] blog 21. 스프링 시큐리티 적용 7 CustomLogoutHandler
- [X] blog 22. refreshToken Cookie로 감싸기
- [X] blog 23. JWT 예외처리
- [X] blog 24. accessToken 재발급
- [X] blog 25. Currying function in Kotlin
- [X] blog 26. Bean static method Access, 회원가입 처리
- [X] blog 27. InMemoryDB 동시성 테스트
- [X] blog 28. Embedded Redis 셋팅
- [X] blog 29. Kotlin JDSL Dynamic Query
- [X] blog 30. @DataJpaTest with Kotlin JDSL
- [X] blog 31. 요청 url/응답 DTO 공통관심사 분리
- [X] blog 32. MDC Logging Filter
- [X] blog 33. File Uploader with LocalFolder
- [X] blog 34. File Uploader with LocalFolder 2
- [X] blog 35. Custom JsonSerializer
- [X] blog 36. Embedded S3 Config
- [X] blog 37. 계층형 댓글 설계 - 1
- [X] blog 38. Service Layer Test With Mockito
- [X] blog 39. 계층형 댓글 설계 - 2
- [ ] blog 40. Redis를 활용한 검색어 자동완성 API
- [ ] blog 41. Caffeine cache 적용
- [ ] blog 42. Custom JsonDeserializer
- [ ] blog 43. 단일모듈에서 멀티모듈 전환 (gradle) 1
- [ ] blog 44. 단일모듈에서 멀티모듈 전환 (gradle) 2
- [ ] blog 45. 단일모듈에서 멀티모듈 전환 (gradle) 3
- [ ] blog 46. 단일모듈에서 멀티모듈 전환 (gradle) 4
- [ ] blog 47. 단일모듈에서 멀티모듈 전환 (gradle) 5
- [ ] blog 48. 단일모듈에서 멀티모듈 전환 (gradle) 6(완)
- [ ] blog 49. entity 속성 추가
- [ ] blog 50. entityListener 활용한 약간의 꼼수?
- [ ] blog 51. TaskScheduler 활용
- [ ] blog 52. Spring Event
- [ ] blog 53. 환경별 profile 분리

## blog 1. 환경설정
docker mariadb 구동
```bash
docker compose -f script/docker-compose.yml up -d
```

앞으로 계획

dev: aws ec2(프리티어) + s3 + codedeploy + github action
back: springboot + kotlin + JPA
front: react + typescript + zustand

## blog 2. JPA Entity 생성
유튜브와 다르게 구성

- Post, Member, BaseEntity 추가 및 JPA 설정
- SpringBoot 3 버전에서 Release된 spring-boot-docker-compose 적용

## blog 3. Entity 연관관계 설정 및 더미데이터 생성
- 더미데이터 생성을 위해 kotlin-faker 라이브러리 추가
- MariaDB Docker Compose 스크립트 오류 수정
- Member MVC 추가
- Post, Comment Entity 추가
- Member Entity 오류 수정

## blog 4. Logback 설정
- p6spy 라이브러리를 활용한 JPA 로그 포멧팅 출력
- Kotlin Logging 적용
- Logback 설정적용

## blog 5. dto entity 맵핑 1
- Logback 설정 수정 및 FILE 설정 추가
- MemberDto 추가 및 InitData 로직 수정

## blog 6. dto entity 맵핑 2
- Member fake 데이터 생성 로직 수정
- Post fake 데이터 생성 로직 추가
- Member 전체 조회 기능 완료
- Post 전체 조회 기능 완료
- Gradle디펜던시 추가
  - `com.fasterxml.jackson.datatype:jackson-datatype-hibernate5`
  - `com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starte`

## blog 7. kotlin-jdsl 살펴보기 + pagenation
- 디펜던시 추가(유튜브에서 안내하는건 최신버전에서 동작하지 않으므로 디버깅후 모듈 교체)
  - `com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter-jakarta`
  - Kotlin Jackson 라이브러리 추가
    - `com.fasterxml.jackson.module:jackson-module-kotlin`
  - Kotlin 테스트 라이브러리 추가
    - `com.ninja-squad:springmockk:4.0.2`
    - `org.jetbrains.kotlin:kotlin-test-junit`
    - `io.kotest:kotest-runner-junit5`
    - `io.kotest:kotest-assertions-core`
    - `io.kotest:kotest-property`
    - `io.kotest.extensions:kotest-extensions-spring`
  - StringUtils 사용을 위한 라이브러리 추가
    - `org.apache.commons:commons-lang3`
  - Test Code에서 Page 응답데이터 처리를 위해 `PageJacksonModule()`, `SortJacksonModule()` 모듈이 필요해 openfeign 추가
    - `org.springframework.cloud:spring-cloud-starter-openfeign`
- Member, Post 서비스 페이징 처리 추가
- Member, Post 테스트 코드 추가
- 유튜브에서 안내하는 라이브러리가 스프링부트 최신버전에서 동작하지 않아 원활한 테스트를 위해 테스트 코드를 추가하고 디버깅 후 라이브러리를 교체하거나 추가함

## blog 8. dto validation
- Docker Desktop 유료화로 Rancher Desktop으로 바꾸면서 docker-compose.yml 스크립트에서 오류가 발생하여 수정
- Member, Post 에 `findById`, `deleteById`, `save` API 및 테스트코드 추가

## blog 9. Custom Exception Handling
- PostSaveDto Validation 적용
- CustomExcetpinHandler 추가
- Validation Error 테스트 코드 추가

## blog 10. AOP Logging Aspect, Fetch Join
- 전통적인 방식의 프록시 기반 스프링 AOP 적용
- Post 조회시 Member Lazy 조회로 두번 조회가 되는 부분 fetch 적용으로 한번에 가져오도록 수정
- initData 등록시 MemberId별 Post데이터가 등록되도록 수정

## blog 11. Filter를 통한 인증처리
- Filter와 HttpSession을 활용해 특정 API Path 인증 처리하기를 간단하게 구현
- 스프링시큐리티라는 필터와 인터셉터를 이용해서 만든 강력한 인증처리 관련 프레임워크가 있음
- SpringBoot 3.1.1 -> 3.2.1, Kotlin 1.8.22 -> 1.9.22 외 Dependency Minor Version Update

## blog 12. 계획
- backend
  1. 비동기 처리
  2. 파일 핸들링
  3. sse event + web socket을 활용한 실시간 챗봇
  4. aws 배포
  5. actuator + admin-server를 통한 간단한 모니터링
  6. code deploy + github action을 통한 CI/CD
  7. 스프링 시큐리티 + JWT 인증처리
  8. Junit + Mockk 테스트 환경설정
  9. restdoc 통한 API 문서 자동화
  10. gradle 멀티모듈을 통해서, domain을 공유하는 Batch 서버 작성
  11. 인메모리 concurrentHashmap을 통한 cache 적용
  12. 계층형 테이블 전략
  13. 스프링 클라우드 모듈들을 활용해서 간단하게 MSA 환경 구축
  14. Docker 연동해서 배포
- frontend
  1. react - typescript 환경셋팅
  2. recoil + zustand를 통한 상태관리
  3. pm2를 활용한 배포, 모니터링
  4. 정적 페이지 서버로서 s3에 배포
  5. next.js?? (미정이긴 한데)를 활용해서 서버사이드랜더링 체험 + SEO
  6. antd를 활용한 ui 컴포넌트 활용
  7. 반응형 스타일링
  8. webpack 최적화 + usecallback을 활용한 랜더링 최적화
- JPA의 N+1문제 ManyToOne의 경우 fetch Join으로 한번의 SQL요청으로 연관관계 데이터를 조회해옴
- OneToMany의 케이스가 여러개인 케이스일 때는 서비스에서 Lazy로딩을 하는데 `default_batch_fetch_size`로 최적화가 필요
  - 이런 문제로 양방향은 추천하지 않고 단방향만 설계하는 것을 권장

## blog 13. 스프링 시큐리티 적용 1
- SpringSecurity 적용
- JwtAuthenticationProvider 구현

## blog 14. 스프링 시큐리티 적용 2 jwt 발급
- email, password 인증 서비스 추가
- 인증 성공시 JWT토큰 생성 및 응답 처리 추가
- 참고
  - https://jwt.io/
  - https://twer.tistory.com/entry/Security-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%9D%98-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EA%B5%AC%EC%A1%B0-%EB%B0%8F-%ED%9D%90%EB%A6%84

## blog 15. 스프링 시큐리티 적용 3 기본인증필터
- 로그인 사용자 유효성 처리 (BasicAuthenticationFilter)

## blog 16. 스프링 시큐리티 적용 4 실패/성공 핸들링
- 토큰 인증 성공/실패 핸들링 처리 추가

## blog 17. 스프링 시큐리티 적용 5 url 기반 인가처리
- Member Role과 URL에 따른 인가처리
- 로그인시 Member 정보 응답

## blog 18. JWT Manager 수정
- JwtAuthenticationProvider Refactoring
- JWT 유효성 검증 로직 수정
- Member Id 파라메터 추가

## blog 19. ObjectMapper config
- JWT 이미 발급된 토큰으로 인증시 DB Access 하지 않도록 개선(서버상의 Claim 으로만 인증)
  - `getPrincipalStringByAccessToken`
- JWT 토큰 ExpireDate 수정 nano -> millis
- JsonUtils Kotlin Module 설정 추가

## blog 20. 스프링 시큐리티 적용 6 메서드 호출 보안
- 좀 더 정밀한 API 보안이 필요한 경우 메서드 단위 설정
  - `@EnableMethodSecurity(securedEnabled = true)`
  - `@Secured(*["ROLE_ADMIN", "ROLE_USER"])`
  - `@PostAuthorize("hasRole('ADMIN') or hasRole('USER')")`
  - `@PreAuthorize("hasRole('ADMIN')")`

## blog 21. 스프링 시큐리티 적용 7 CustomLogoutHandler
- SpringSecurity LogOutHandler 적용
- Member 객체 Refactoring

## blog 22. refreshToken Cookie로 감싸기
- Refresh Token 추가
- Cookie로 Refresh Token을 감싸서 응답되도록 처리

## blog 23. JWT 예외처리
- JWT 예외처리 및 응답처리 리팩토링

## blog 24. accessToken 재발급
- accessToken 만료시 refreshToken을 통해 accessToken 재발급 처리

## blog 25. Currying function in Kotlin
- CustomBasicAuthenticationFilter에 Currying function을 활용한 함수형 프로그래밍 적용

## blog 26. Bean static method Access, 회원가입 처리
- 회원가입 서비스 AuthService로 이동
- rawPassword encode 처리를 위해 BeanAccessor 추가

## blog 27. InMemoryDB 동시성 테스트
- InMemoryDB 동시성 테스트
- jwt expire 설정값 application.yml 설정으로 변경
- 유효기간을 가지면서 동시성을 지원해주는 HashMap 라이브러리(`expiringmap`) 추가
- ExpireMap을 활용한 RefreshToken 관리

## blog 28. Embedded Redis 셋팅
- Embedded Redis 셋팅
- Embedded Redis를 활용한 테스트 코드 추가

## blog 29. Kotlin JDSL Dynamic Query
- refreshToken 저장시 JsonString으로 변환하여 저장하여 로그인 오류 해결
- Kotlin JDSL Dynamic Query 적용

## blog 30. @DataJpaTest with Kotlin JDSL
- searchCondition 조건으로 조회하는 Dynamic Query 추가
- Kotlin JDSL 적용 및 테스트

## blog 31. 요청 url/응답 DTO 공통관심사 분리
- 공통 관심사 분리 interceptor, filter, aop
  - aop: 로직의 시간측정, 트랜잭션 관리, 특정 클래스나 패키지 별로 처리가 가능
  - filter: 공통된 보안/인가(SpringSecurity) 처리, 이미지와 데이터, 문자열 인코딩, 모든 요청에 대한 로깅
    - 스프링과 좀 분리가 되어야 하는 기능들에 대해서 필터 처리
  - interceptor: Spring Container에 있는 것들만 Target으로 함, filter 같은 경우에는 Spring Container에서 동작하는 것이니까 결국 모든 요청을 앞단에서 받을 수 있음 
    - 모든 요청에 대한 로깅, 세부적인 보안 및 인증 인가 처리
    - 컨트롤러로 넘기는 데이터를 가공, Response Stream을 건드릴 수는 없음
- API 버전 관심사 분리
- API 응답시 resultMsg에 API 버전을 추가

## blog 32. MDC Logging Filter
- 추적용 로깅 필터 추가
  - Filter에서 사용자별 고유한 UUID를 생성하여 로그를 추적할 수 있음

## blog 33. File Uploader with LocalFolder
- 파일 업로드 서비스 추가
- 파일 업로드 테스트 코드 추가
- 패키지 명 수정 api -> web

## blog 34. File Uploader with LocalFolder 2
- 파일 업로드시 파일명 UUID 로 저장되도록 수정
- 스크립트 코드 정리
- 파일 업로드 후 이미지 URL 응답 추가
- member 생성일, 수정일 응답 포맷 설정

## blog 35. Custom JsonSerializer
- id, createdAt, updatedAt 공통 속성 BaseDto, BaseEntity 로 리팩토링
- Custom JsonSerializer 로 객체 응답시 LocalDateTime JSON Format 공통 설정

## blog 36. Embedded S3 Config
- S3Mock 설정
- Embedded S3 업로드 기능 추가
- LocalFileUpload -> LocalS3FileUploader

## blog 37. 계층형 댓글 설계 - 1
- 댓글 등록 기능 추가
- 계층형 댓글 등록 기능 추가

## blog 38. Service Layer Test With Mockito
- Mockito 테스트 코드 작성 및 테스트

## blog 39. 계층형 댓글 설계 - 2
- 계층형 댓글 코드 작성 및 테스트
- 참고(Closure Table)
  - https://wildeveloperetrain.tistory.com/150
  - https://github.com/line/kotlin-jdsl
  - https://kotlin-jdsl.gitbook.io/docs