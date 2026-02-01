# Follow 서비스 구현 계획

## 개요
사용자 간 팔로우/팔로잉 기능 및 팔로우 카운트 조회 서비스

## 도메인 설계

> 모든 엔티티는 `BaseEntity`를 상속하여 `createdAt`, `updatedAt`, `deletedAt` 필드를 공통으로 가진다.

### Follow Entity (extends BaseEntity)
- `id`: Long (PK)
- `follower`: User (팔로우 하는 사람)
- `following`: User (팔로우 당하는 사람)

### FollowCount Entity (extends BaseEntity, 비정규화 카운트 테이블)
- `id`: Long (PK)
- `user`: User
- `followersCount`: Long (나를 팔로우하는 수)
- `followeesCount`: Long (내가 팔로우하는 수)

## API 설계

> 사용자 정보는 쿠키(세션)를 통해 인증된 사용자로부터 받는다.

| Method | Endpoint | 설명 |
|--------|----------|------|
| POST | `/api/v1/follow/{targetId}` | 팔로우 |
| DELETE | `/api/v1/follow/{targetId}` | 언팔로우 |
| GET | `/api/v1/follow/followers` | 내 팔로워 목록 조회 |
| GET | `/api/v1/follow/followees` | 내 팔로잉 목록 조회 |
| GET | `/api/v1/follow/count` | 내 팔로우 카운트 조회 |

## 구현 파일 목록

### Domain
- `domain/follow/Follow.java`
- `domain/follow/FollowRepository.java`
- `domain/follow/FollowCount.java`
- `domain/follow/FollowCountRepository.java`
- `domain/follow/FollowService.java`
- `domain/follow/FollowException.java`

### Controller & DTO
- `controller/FollowController.java`
- `controller/dto/FollowResponse.java`
- `controller/dto/FollowCountResponse.java`
- `controller/dto/FollowerListResponse.java`

## 주요 비즈니스 로직
1. 자기 자신 팔로우 불가
2. 중복 팔로우 불가
3. 팔로우/언팔로우 시 FollowCount 자동 업데이트
4. 팔로워/팔로잉 목록 페이징 처리
5. FollowCount 업데이트 시 원자적 연산으로 갱신손실(Lost Update) 방지
   - `UPDATE follow_counts SET followers_count = followers_count + 1 WHERE user_id = ?` 형태의 쿼리 사용
   - `@Modifying` + `@Query`로 DB 레벨에서 원자적 처리
