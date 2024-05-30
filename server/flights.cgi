#!/usr/bin/env bash
# echo "Content-type: plain/text"
echo "Content-type: application/json"

echo "Access-Control-Allow-Origin: *"
echo ""

java FlightFrontend "$QUERY_STRING"
