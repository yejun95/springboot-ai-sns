#!/bin/bash

BASE_URL="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COOKIES_FILE="$SCRIPT_DIR/cookies.txt"

echo "=== Quote API Test ==="
echo ""

# 1. Create Quote
echo "1. Create Quote for Post ID: 1"
curl -X POST "${BASE_URL}/api/v1/quotes" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "quoteId": 1,
    "content": "I agree with this post!"
  }'
echo -e "\n"

# 2. Get Quote by ID
echo "2. Get Quote by ID (ID: 3)"
curl -X GET "${BASE_URL}/api/v1/quotes/3" \
  -b "$COOKIES_FILE"
echo -e "\n"

# 3. Update Quote
echo "3. Update Quote (ID: 3)"
curl -X PUT "${BASE_URL}/api/v1/quotes/3" \
  -H "Content-Type: application/json" \
  -b "$COOKIES_FILE" \
  -d '{
    "content": "Updated quote content"
  }'
echo -e "\n"

# 4. Delete Quote
echo "4. Delete Quote (ID: 3)"
curl -X DELETE "${BASE_URL}/api/v1/quotes/3" \
  -b "$COOKIES_FILE"
echo -e "\n"

echo "=== Quote API Test Complete ==="
