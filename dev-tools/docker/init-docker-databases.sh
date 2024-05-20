#!/bin/bash

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd)
DATABASE_NAME="mpc-mysql"

docker exec -i "$DATABASE_NAME" sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD"' < "${SCRIPT_DIR}/../../mpc-backend/sqls/init-database.sql"
