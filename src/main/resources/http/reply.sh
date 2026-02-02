#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

echo "=== Reply API Test ==="
echo ""

# 1. Create Reply
echo "1. Create Reply to Post ID: 1"
curl -X POST "${BASE_URL}/api/v1/replies" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "parentId": 1,
    "content": "This is a reply to the post"
  }'
echo -e "\n"

# 2. Get Replies by Post ID
echo "2. Get Replies by Post ID (postId: 1)"
curl -X GET "${BASE_URL}/api/v1/replies/post/1" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 3. Update Reply
echo "3. Update Reply (ID: 2)"
curl -X PUT "${BASE_URL}/api/v1/replies/2" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "content": "Updated reply content"
  }'
echo -e "\n"

# 4. Delete Reply
echo "4. Delete Reply (ID: 2)"
curl -X DELETE "${BASE_URL}/api/v1/replies/2" \
  -b "$COOKIES_FILE"
echo -e "\n"

echo "=== Reply API Test Complete ==="
