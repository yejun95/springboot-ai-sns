#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

# 로그인 - testuser
login_testuser() {
  echo "Logging in as testuser..."
  curl -X POST "$BASE_URL/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=testuser&password=password123" \
    -c "$COOKIES_FILE" \
    -v
}

# 로그인 - user2
login_user2() {
  echo "Logging in as user2..."
  curl -X POST "$BASE_URL/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=user2&password=password123" \
    -c "$COOKIES_FILE" \
    -v
}

# 로그아웃
logout() {
  echo "Logging out..."
  curl -X POST "$BASE_URL/logout" \
    -b "$COOKIES_FILE" \
    -c "$COOKIES_FILE" \
    -v
}

# 현재 사용자 정보 확인
check_session() {
  echo "Checking current session..."
  curl -X GET "$BASE_URL/api/v1/users/me" \
    -b "$COOKIES_FILE" \
    -v
}

# 쿠키 파일 삭제
clear_cookies() {
  echo "Clearing cookies..."
  rm -f "$COOKIES_FILE"
  echo "Cookies cleared."
}

# 사용법 출력
usage() {
  echo "Usage: $0 {login_testuser|login_user2|logout|check|clear}"
}

case "$1" in
  login_testuser)
    login_testuser
    ;;
  login_user2)
    login_user2
    ;;
  logout)
    logout
    ;;
  check)
    check_session
    ;;
  clear)
    clear_cookies
    ;;
  *)
    login_testuser
    ;;
esac
