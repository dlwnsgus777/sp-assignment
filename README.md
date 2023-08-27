# 스푼 라디오 과제 전형


## How to run

```shell
$ unzip sp-assignment-main.zip
$ cd sp-assignment-main/spoon
$ ./gradlew clean build
$ java -jar build/libs/spoon-0.0.1-SNAPSHOT.jar
```

## 검증 방법
- [Swagger](http://localhost:8080/docs/swagger)를 통해 API를 테스트할 수 있습니다.

### Swagger 테스트 방법
- [회원 가입](http://localhost:8077/docs/swagger-ui/index.html#/Authentication%20api/signUp) 진행
- 회원가입 완료 후 [로그인](http://localhost:8077/docs/swagger-ui/index.html#/Authentication%20api/signIn) 진행
- 로그인 완료 시 응답되는 `token`을 `Authorize` 버튼을 통해 입력
    - ![image](docs/images/jwt_example.png)
- [내방 관련 api](http://localhost:8077/docs/swagger-ui/index.html#/Room%20Api)를 통해 내방 관련 api 를 확인할 수 있습니다.

## Skills
- Java 11
- Spring Boot 2.7.14
- Spring Data JPA
- H2 Database
- Swagger

## API Docs
- [Swagger](http://localhost:8080/docs/swagger)
