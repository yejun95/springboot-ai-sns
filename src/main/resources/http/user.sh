#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

# 회원가입 - testuser
register_testuser() {
  echo "Creating testuser..."
  curl -X POST "$BASE_URL/api/v1/users/signup" \
    -H "Content-Type: application/json" \
    -d '{
      "username": "testuser",
      "password": "password123"
    }'

  echo -e "\n\nLogging in as testuser..."
  curl -X POST "$BASE_URL/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=testuser&password=password123" \
    -c "$COOKIES_FILE" \
    -v
}

# 회원가입 - user2
register_user2() {
  echo "Creating user2..."
  curl -X POST "$BASE_URL/api/v1/users/signup" \
    -H "Content-Type: application/json" \
    -d '{
      "username": "user2",
      "password": "password123"
    }'

  echo -e "\n\nLogging in as user2..."
  curl -X POST "$BASE_URL/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=user2&password=password123" \
    -c "$COOKIES_FILE" \
    -v
}

# 두 사용자 모두 회원가입
register_all() {
  echo "Creating testuser..."
  curl -X POST "$BASE_URL/api/v1/users/signup" \
    -H "Content-Type: application/json" \
    -d '{
      "username": "testuser",
      "password": "password123"
    }'

  echo -e "\n\nCreating user2..."
  curl -X POST "$BASE_URL/api/v1/users/signup" \
    -H "Content-Type: application/json" \
    -d '{
      "username": "user2",
      "password": "password123"
    }'

  echo -e "\n\nLogging in as testuser..."
  curl -X POST "$BASE_URL/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=testuser&password=password123" \
    -c "$COOKIES_FILE" \
    -v
}

# 사용자 조회
get_user() {
  curl -X GET "$BASE_URL/api/users/$1"
}

# 전체 사용자 조회
get_all_users() {
  curl -X GET "$BASE_URL/api/users"
}

# 프로필 수정
update_profile() {
  curl -X PUT "$BASE_URL/api/users/$1" \
    -H "Content-Type: application/json" \
    -d '{
      "nickname": "updateduser",
      "bio": "Hello, I am updated!"
    }'
}

# 사용자 삭제
delete_user() {
  curl -X DELETE "$BASE_URL/api/users/$1"
}

# 사용법 출력
usage() {
  echo "Usage: $0 {register_testuser|register_user2|register_all|get <id>|get_all|update <id>|delete <id>}"
}

case "$1" in
  register_testuser)
    register_testuser
    ;;
  register_user2)
    register_user2
    ;;
  register_all)
    register_all
    ;;
  get)
    get_user "$2"
    ;;
  get_all)
    get_all_users
    ;;
  update)
    update_profile "$2"
    ;;
  delete)
    delete_user "$2"
    ;;
  *)
    register_all
    ;;
esac
