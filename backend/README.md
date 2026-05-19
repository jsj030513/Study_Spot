# Study Spot Backend

Java Spring Boot 기반 Study Spot API 서버입니다. 기존 프론트(`html`, `css`, `js`)와 DB SQL 파일은 수정하지 않고, `backend` 폴더 안에만 구현했습니다.

## 준비

1. JDK 17 이상 설치
2. MySQL 실행
3. `db/cafe_study.sql`을 MySQL에 import
4. `src/main/resources/application.properties`의 DB 계정 확인

현재 터미널에서 `java` 명령이 잡히지 않으면 JDK 설치 폴더의 `bin` 경로를 PATH에 추가하거나 VS Code를 재시작해주세요.

## 실행

```bash
cd backend
mvn spring-boot:run
```

Windows에서 프로젝트 경로에 한글이 포함되어 `spring-boot:run`이 메인 클래스를 못 찾으면 아래 방식으로 실행하세요.

```powershell
cd backend
.\run.ps1
```

또는 직접 실행할 수도 있습니다.

```powershell
mvn clean package -DskipTests
java -jar target\study-spot-backend-0.0.1-SNAPSHOT.jar
```

## API 요약

- `POST /api/auth/signup` 회원가입
- `POST /api/auth/login` 로그인
- `GET /api/users/me` 내 정보 조회
- `PATCH /api/users/me` 내 정보 수정
- `DELETE /api/users/me` 회원 탈퇴
- `GET /api/admin/users` 관리자 회원 조회
- `PATCH /api/admin/users/{userId}` 관리자 회원 수정
- `DELETE /api/admin/users/{userId}` 관리자 회원 삭제
- `GET /api/cafes` 장소 검색
- `GET /api/cafes/{cafeId}` 장소 상세
- `POST /api/cafes` 관리자 장소 등록
- `PATCH /api/cafes/{cafeId}` 관리자 장소 수정
- `DELETE /api/cafes/{cafeId}` 관리자 장소 삭제
- `GET /api/recommendations/cafes` 조건 기반 추천

관리자 API와 장소 등록/수정/삭제는 로그인 응답의 토큰을 `Authorization: Bearer 토큰` 헤더로 보내야 하며, `ROLE_TY`가 `A`인 사용자만 사용할 수 있습니다.
