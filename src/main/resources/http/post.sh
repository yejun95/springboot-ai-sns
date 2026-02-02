#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

echo "=== Post API Test ==="
echo ""

# 1. Create Post
echo "1. Create Post"
curl -X POST "${BASE_URL}/api/v1/posts" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "content": "This is my first post!"
  }'
echo -e "\n"

# 2. Get Post by ID
echo "2. Get Post by ID (ID: 1)"
curl -X GET "${BASE_URL}/api/v1/posts/1" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 3. Get Timeline Posts (paginated)
echo "3. Get Timeline Posts (page=0, size=20)"
curl -X GET "${BASE_URL}/api/v1/posts?page=0&size=20" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 4. Get Posts by User ID
echo "4. Get Posts by User ID (userId: 1)"
curl -X GET "${BASE_URL}/api/v1/posts/user/1?page=0&size=20" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 5. Update Post
echo "5. Update Post (ID: 1)"
curl -X PUT "${BASE_URL}/api/v1/posts/1" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "content": "Updated post content"
  }'
echo -e "\n"

# 6. Delete Post
echo "6. Delete Post (ID: 1)"
curl -X DELETE "${BASE_URL}/api/v1/posts/1" \
  -b "$COOKIES_FILE"
echo -e "\n"

echo "=== Post API Test Complete ==="
