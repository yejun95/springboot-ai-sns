#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

# 팔로우
follow() {
  if [ -z "$1" ]; then
    echo "Usage: $0 follow <targetId>"
    return 1
  fi

  echo "Following user $1..."
  curl -X POST "$BASE_URL/api/v1/follow/$1" \
    -b "$COOKIES_FILE" \
    -v
}

# 언팔로우
unfollow() {
  if [ -z "$1" ]; then
    echo "Usage: $0 unfollow <targetId>"
    return 1
  fi

  echo "Unfollowing user $1..."
  curl -X DELETE "$BASE_URL/api/v1/follow/$1" \
    -b "$COOKIES_FILE" \
    -v
}

# 내 팔로워 목록
get_followers() {
  echo "Getting my followers..."
  curl -X GET "$BASE_URL/api/v1/follow/followers" \
    -b "$COOKIES_FILE" \
    -v
}

# 내가 팔로우하는 사람 목록
get_followees() {
  echo "Getting my followees..."
  curl -X GET "$BASE_URL/api/v1/follow/followees" \
    -b "$COOKIES_FILE" \
    -v
}

# 팔로우 수 조회
get_count() {
  echo "Getting follow count..."
  curl -X GET "$BASE_URL/api/v1/follow/count" \
    -b "$COOKIES_FILE" \
    -v
}

# 모든 정보 보기
show_all() {
  echo "=== My Follow Information ==="
  echo ""

  echo "--- Follow Count ---"
  get_count

  echo ""
  echo ""
  echo "--- My Followers ---"
  get_followers

  echo ""
  echo ""
  echo "--- My Followees ---"
  get_followees
}

# 사용법 출력
usage() {
  echo "Usage: $0 {follow <id>|unfollow <id>|followers|followees|count}"
}

case "$1" in
  follow)
    follow "$2"
    ;;
  unfollow)
    unfollow "$2"
    ;;
  followers)
    get_followers
    ;;
  followees)
    get_followees
    ;;
  count)
    get_count
    ;;
  *)
    show_all
    ;;
esac
