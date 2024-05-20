#!/bin/bash

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd)
DATABASE_NAME="mpc-mysql"

if [ "$(docker container ls -a | grep -c "$DATABASE_NAME")" -gt 0 ]; then
   docker start "$DATABASE_NAME" > /dev/null
else
  docker run --name "$DATABASE_NAME" -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -v "${SCRIPT_DIR}/mysql.conf.d":/etc/mysql/conf.d/ -d mysql:8.0
fi
