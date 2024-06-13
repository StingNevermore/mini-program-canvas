create table `tenant`
(
    `tenant_id`   varchar(10)  not null comment '租户ID',
    `tenant_name` varchar(254) not null comment '租户名称',
    primary key (`tenant_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_bin;

create table `operator`
(
    `operator_id`   bigint       not null comment '租户ID',
    `operator_name` varchar(254) not null comment '租户名称',
    `tenant_id`     varchar(10)  not null default '' comment '所属租户',
    primary key (`operator_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_bin;
