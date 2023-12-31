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
- [ ] blog 11. Filter를 통한 인증처리
- [ ] blog 12. 계획
- [ ] blog 13. 스프링 시큐리티 적용 1
- [ ] blog 14. 스프링 시큐리티 적용 2 jwt 발급
- [ ] blog 15. 스프링 시큐리티 적용 3 기본인증필터
- [ ] blog 16. 스프링 시큐리티 적용 4 실패/성공 핸들링
- [ ] blog 17. 스프링 시큐리티 적용 5 url 기반 인가처리
- [ ] blog 18. JWT Manager 수정
- [ ] blog 19. ObjectMapper config
- [ ] blog 20. 스프링 시큐리티 적용 6 메서드 호출 보운
- [ ] blog 21. 스프링 시큐리티 적용 7 CustomLogoutHandler
- [ ] blog 22. refreshToken Cookie로 감싸기
- [ ] blog 23. JWT 예외처리
- [ ] blog 24. accessToken 재발급
- [ ] blog 25. Currying function in Kotlin
- [ ] blog 26. Bean static method Access, 회원가입 처리
- [ ] blog 27. InMemoryDB 동시성 테스트
- [ ] blog 28. Embedded Redis 셋팅
- [ ] blog 29. Kotlin JDSL Dynamic Query
- [ ] blog 30. @DataJpaTest with Kotlin JDSL
- [ ] blog 31. 요청 url/응답 DTO 공통관심사 분리
- [ ] blog 32. MDC Logging Filter
- [ ] blog 33. File Uploader with LocalFolder
- [ ] blog 34. File Uploader with LocalFolder 2
- [ ] blog 35. Custom JsonSerializer
- [ ] blog 36. Embedded S3 Config
- [ ] blog 37. 계층형 댓글 설계 - 1
- [ ] blog 38. Service Layer Test With Mockito
- [ ] blog 39. 계층형 댓글 설계 - 2
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