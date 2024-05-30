#!/usr/bin/env bash
# echo "Content-type: plain/text"
echo "Content-type: application/json"

echo "Access-Control-Allow-Origin: *"
echo ""

cat "$QUERY_STRING/airports_airlines.json"
echo ""
