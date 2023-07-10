# Simple SNS

## 요구사항 분석
- POST LIST API
  - 모든 user의 글을 모아서 보여주는 API
  - Paging 및 정렬
  - 제목 , 작성자 , 본문을 가져옴
- POST CREATE API
  - 제목과 본문을 이용하여 포스트 작성
- User Sign Up API
  - User name과 password를 이용하여 회원가입
- User Sign In API
  - User name과 password를 이용하여 로그인
- My Post API
  - 로그인한 user의 글을 모아서 보여주는 API
  - Paging 및 정렬
  -  제목, 작성자, 본문을 가져옴
- Post Delete API
  - 포스트를 삭제하는 API
- Post Modify API
  - 제목과 본문을 수정할 수 있는 API
- SSE(Sever-Sent Event) 를 사용하여 실시간 알람 기능

## Tech Stack

- Intellij IDEA Ultimate
- Java 11
- Spring Boot 2.6.7
- Spring Boot Actuator
- Spring Data JPA
- JWT
- postgresql
- hibernate-types
- Spring Security
- Lombok
- CloudKaraka
- react
- Heroku

## Flow Chart

1. 회원가입

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 회원가입 요청
    alt 성공한 경우
    server -->> client: 성공 반환
    else 아이디가 중복된 경우 
    server -->> client: reason code와 함께 실패 반환
    end
```

2. 로그인

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 로그인
    alt 성공한 경우 
    server ->> client: 성공 반환
    else 아이디가 존재하지 않는 경우 
    server -->> client: reason code와 함께 실패 반환
    else 패스워드가 틀린 경우
    server -->> client: reason code와 함께 실패 반환
    end
```

3. 포스트 작성

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 포스트 작성 요청
    alt 성공한 경우 
    server ->> db : 포스트 저장 요청
    db -->> server : 저장 성공 반환
    server -->> client: 성공 반환
    else 로그인하지 않은 경우
    server -->> client: reason code와 함께 실패 반환
    else db 에러
    server ->> db : 포스트 저장 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    else 내부 에러
    server -->> client: reason code와 함께 실패 반환
    end
```

4. 포스트 삭제

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 포스트 삭제 요청
    alt 성공한 경우 
    server ->> db : 포스트 삭제 요청
    db -->> server : 삭제 성공 반환
    server -->> client: 성공 반환
    else 로그인하지 않은 경우
    server -->> client: reason code와 함께 실패 반환
    else db 에러
    server ->> db : 포스트 삭제 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    else 내부 에러
    server -->> client: reason code와 함께 실패 반환
    end
```

5. 포스트 수정

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 포스트 수정 요청
    alt 성공한 경우 
    server ->> db : 포스트 수정 요청
    db -->> server : 수 성공 반환
    server -->> client: 성공 반환
    else 로그인하지 않은 경우
    server -->> client: reason code와 함께 실패 반환
    else db 에러
    server ->> db : 포스트 수정 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    else 내부 에러
    server -->> client: reason code와 함께 실패 반환
    end
```

6. 피드 목록

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 피드 목록 요청
    alt 성공한 경우 
    server ->> db : 포스트 목록 요청
    db -->> server : 목록 쿼리 성공 반환
    server -->> client: 목록 반환
    else 로그인하지 않은 경우
    server -->> client: reason code와 함께 실패 반환
    else db 에러
    server ->> db : 포스트 목록 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    else 내부 에러
    server -->> client: reason code와 함께 실패 반환
    end
```

7. 좋아요 기능 : User A가 B 게시물에 좋아요를 누른 상황

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 좋아요 요청 
    alt 성공한 경우 
    server ->> db : db update 요청
    db -->> server : 성공 반환
    server -->> client: 성공 반환
    
    else 로그인하지 않은 경우
    server -->> client: reason code와 함께 실패 반환
    
    else db 에러
    server ->> db : db update 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    
    else B 게시물이 존재하지 않는 경우 
    server ->> db : db update 요청
    db -->> server : 에러 반환
    server -->> client: reason code와 함께 실패 반환
    
    else 내부 에러
    server -->> client: reason code와 함께 실패 반환
    end
```

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 좋아요 요청 
    alt 성공한 경우 
    server ->> db : 좋아요 누를 수 있는 조건 체크 
    db -->> server : 응답 
    server --) kafka : event produce request
    server->>client: 성공 반환
    kafka --) server : event consume 
    server ->> db : db update 요청
    db -->> server : 성공 반환 
    
    else 로그인하지 않은 경우
    server->>client: reason code와 함께 실패 반환
    
    else db 에러
    server ->> db : 좋아요 누를 수 있는 조건 체크 
    db -->> server : 응답 
    server --) kafka : event produce request
    server->>client: 성공 반환
    kafka --) server : event consume 
    loop db update 실패시 최대 3회 재시도 한다
        server ->> db : db update 요청
    end
    db -->> server : 실패 반환 
    
    else B 게시물이 존재하지 않는 경우 
    server ->> db : 좋아요 누를 수 있는 조건 체크 
    db ->> server : 에러 반환
    server->>client: reason code와 함께 실패 반환
    
    else 내부 에러
    server->>client: reason code와 함께 실패 반환
    end
```

8. 댓글 기능 : User A가 B 게시물에 댓글을 남긴 상황

```mermaid
sequenceDiagram
autonumber
client ->> server: 댓글 작성
    alt 성공한 경우
    server ->> db : db update 요청
    db ->> server : 성공 반환
    server->>client: 성공 반환

    else 로그인하지 않은 경우
    server->>client: reason code와 함께 실패 반환
    
    else db 에러
    server ->> db : db update 요청
    db ->> server : 에러 반환
    server->>client: reason code와 함께 실패 반환
    
    else B 게시물이 존재하지 않는 경우 
    server ->> db : db update 요청
    db ->> server : 에러 반환
    server->>client: reason code와 함께 실패 반환
    
    else 내부 에러
    server->>client: reason code와 함께 실패 반환
    end
```

```mermaid
  sequenceDiagram
    autonumber
    client ->> server: 댓글 작성
    alt 성공한 경우 
    server ->> db : 좋아요 누를 수 있는 조건 체크 
    db -->> server : 응답 
    server --) kafka : event produce request
    server->>client: 성공 반환
    kafka --) server : event consume 
    server ->> db : db update 요청
    db -->> server : 성공 반환 
    
    else 로그인하지 않은 경우
    server->>client: reason code와 함께 실패 반환
    
    else db 에러
    server ->> db : 댓글 작성 조건 체크 
    db -->> server : 응답 
    server --) kafka : event produce request
    server->>client: 성공 반환
    kafka --) server : event consume 
    loop db update 실패시 최대 3회 재시도 한다
        server ->> db : db update 요청
    end
    db -->> server : 실패 반환 
    
    else B 게시물이 존재하지 않는 경우 
    server ->> db : 댓글 작성 조건 체크 
    db ->> server : 에러 반환
    server->>client: reason code와 함께 실패 반환
    
    else 내부 에러
    server->>client: reason code와 함께 실패 반환
    end
```

9. 알람 기능 : User A의 알람 목록에 대한 요청을 한 상황

```mermaid
sequenceDiagram
autonumber
client ->> server: 알람 목록 요청 
    alt 성공한 경우
    server ->> db : db query 요청
    db ->> server : 성공 반환
    server->>client: 성공 반환

    else 로그인하지 않은 경우
    server->>client: reason code와 함께 실패 반환
    
    else db 에러
    server ->> db : db query 요청
    db ->> server : 에러 반환
    server->>client: reason code와 함께 실패 반환

    else 내부 에러
    server->>client: reason code와 함께 실패 반환
    end
```
