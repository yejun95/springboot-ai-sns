#!/bin/bash

BASE_URL="http://localhost:8080"

# 회원가입
register() {
  curl -X POST "$BASE_URL/api/users" \
    -H "Content-Type: application/json" \
    -d '{
      "email": "test@example.com",
      "password": "password123",
      "nickname": "testuser"
    }'
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
  echo "Usage: $0 {register|get <id>|get_all|update <id>|delete <id>}"
}

case "$1" in
  register)
    register
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
    usage
    ;;
esac
