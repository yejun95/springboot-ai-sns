#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

echo "=== Like API Test ==="
echo ""

# 1. Like Post
echo "1. Like Post (postId: 1)"
curl -X POST "${BASE_URL}/api/v1/posts/1/like" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 2. Check if Liked
echo "2. Check if Post is Liked (postId: 1)"
curl -X GET "${BASE_URL}/api/v1/posts/1/like/check" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 3. Unlike Post
echo "3. Unlike Post (postId: 1)"
curl -X DELETE "${BASE_URL}/api/v1/posts/1/like" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 4. Check if Liked again
echo "4. Check if Post is Liked after Unlike (postId: 1)"
curl -X GET "${BASE_URL}/api/v1/posts/1/like/check" \
  -b "$COOKIES_FILE"
echo -e "\n"

echo "=== Like API Test Complete ==="
