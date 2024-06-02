#!/bin/bash

MYSQL=mariadb
$MYSQL -uroot -p"123456" -h mpc-db < "/init-database.sql"
