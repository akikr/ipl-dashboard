#!/bin/bash
envsubst < /tmp/config.prod.js > /usr/share/nginx/html/config.js
nginx -g 'daemon off;'