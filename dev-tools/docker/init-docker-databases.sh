#!/bin/bash

#docker exec -i "$DATABASE_NAME" sh -c 'exec mysql -uroot -p"$MYSQL_ROOT_PASSWORD"' < "${SCRIPT_DIR}/../../mpc-backend/sqls/init-database.sql"
mysql -uroot -p"123456" -h db < "/init-database.sql"
