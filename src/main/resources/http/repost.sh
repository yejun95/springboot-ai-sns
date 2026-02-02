#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

echo "=== Repost API Test ==="
echo ""

# 1. Create Repost
echo "1. Create Repost for Post ID: 1"
curl -X POST "${BASE_URL}/api/v1/reposts" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "repostId": 1
  }'
echo -e "\n"

# 2. Get Repost by ID
echo "2. Get Repost by ID (ID: 4)"
curl -X GET "${BASE_URL}/api/v1/reposts/4" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 3. Delete Repost
echo "3. Delete Repost (ID: 4)"
curl -X DELETE "${BASE_URL}/api/v1/reposts/4" \
  -b "$COOKIES_FILE"
echo -e "\n"

echo "=== Repost API Test Complete ==="
