# Daily Word : 일일 큐티 모바일 애플리케이션 프로젝트

### 개발 기간 : 2025-05-26 ~ 현재 진행중

--- 

## 개발 목표
대학생 때, 처음으로 아침 큐티를 진행하고, 자신의 감상을 나눔하는 과정이 좋았던 기억이 있었고, 다른 사람들은 이 문구를 보고 어떤 감상을 할지, 자신의 인생을 어떻게 돌아보는지에 대한 궁금증이 생겼습니다.
<br>
<br>
또한, 큐티책을 계속해서 쌓아가는 것이 도움이 되지만, 이걸 모바일로 이식한다면, 더 쉽게 내 예전 감상들을 돌아볼 수 있게 해주는 방법이 될 것이라고 생각했습니다.
<br>
### 이 프로젝트를 통해 여러분들의 신학 친구를 만들어보세요. 
해당 프로젝트는 API 통신을 통한 멀티 모듈 프로젝트와 헥사고날 아키텍쳐, 모바일 앱 개발을 위한 Flutter 대한 공부를 위해 시작한 프로젝트입니다.

---

## 개발 스펙

### Back-End
- Project Setting : JAVA 21 + Spring Boot 3.4.5 + Gradle 8.13 
- IDE : Intellij
- Version Control : GitHub
- DataBase : PostgreSQL + MongoDB + Redis
- Logging : LogBack
- Server Monitering : Grafana
- File Upload : AWS S3
- Cloud Server : Oracle Cloud


### Front-End

- Project Setting : Flutter
- Editor : Visual Studio Code

### External API

- Social Login : OAuth2 + Kakao
- Daily QT Word Recommend : OpenAI API

---

## 모듈 설명

- 백엔드 프로젝트에 구현되어 있는 모듈을 설명하는 파트입니다.

---
### Gateway

- 유일한 애플리케이션과의 통신 지점입니다. (Controller 위치)
- 해당 모듈로 진입한 사용자의 요청은 내부 모듈로 API 통신을 통해 반환 데이터를 생성한 후 반환하도록 돕습니다.

---
### Common

- 공통 모듈입니다.
- BaseEntity, Logging과 같은 모든 모듈에서 사용해야 하는 코드들이 위치하고 있습니다.

#### 구현 기능

- [X] Base Entity
- [X] Create Uuid Process
- [X] Basic Response Entity
- [X] Logging System

---
### Module-Auth

- JWT를 관리하는 모듈입니다.

#### 구현 기능

- [X] 토큰 발급
- [X] 토큰 검증
- [X] 토큰 재발급

---
### Module-Follow

- 회원 팔로우 기능을 담당하는 모듈입니다.

#### 구현 기능

- [X] 회원 팔로우 API
- [X] 회원 언팔로우 API
- [X] 팔로워, 팔로잉 수 반환 API
- [X] 팔로워 리스트 반환 API
- [X] 팔로윙 리스트 반환 API
- [ ] 팔로우 Entity 내부 닉네임 및 사진 저장 처리

---
### Module-Member

- 회원 정보에 관한 전반적인 기능을 담당하는 모듈입니다.

#### 구현 기능
- [X] RefCode -> Id (PK) 값 반환 API
- [X] 로그인 처리 API
- [ ] 회원 정보 수정 API
- [ ] 회원 정보 반환 API

---
### Module-Post

- 게시글과 댓글에 관한 기능을 담당하는 모듈입니다.

#### 구현 기능

- Post 
- [X] 게시글 생성 API
- [X] 게시글 수정 API
- [X] 게시글 삭제 API
- [X] 게시글 상세 조회 API
- [X] 게시글 목록 조회 API
- [ ] 특정 회원 게시글 목록 조회 API
- [ ] 게시글 신고 API

- Comment
- [X] 댓글 추가 API
- [ ] 댓글 수정 API
- [X] 댓글 삭제 API
- [X] 댓글 목록 조회 API

---
### Module-Qt

- OpenAI를 사용해 추천 말씀을 정리하는 기능을 담당하는 모듈입니다.

#### 구현 기능

- [ ] 큐티 말씀 묶음 정리 API
- [ ] 큐티 말씀 추천 API

---
### Module-kakao

- OAuth를 사용한 카카오 소셜 로그인 기능을 담당하는 모듈입니다.

#### 구현 기능

- [X] 카카오 소셜 로그인 처리 API

---
### Module-version

- 모바일 애플리케이션 버전 확인 기능을 담당하는 모듈입니다.

#### 구현 기능

- [ ] 업데이트 필요 유무 확인 API

---
### Module-File

- 파일 업로드 및 삭제 관련 AWS S3에 관한 모듈입니다.

#### 구현 기능

- [ ] 파일 업로드
- [ ] 파일 삭제

---
### Module-Admin

- 관리자 페이지 기능을 담당하는 모듈입니다.

#### 구현 기능

---
