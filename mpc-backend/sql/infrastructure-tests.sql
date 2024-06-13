create table `test`
(
    `id`    bigint auto_increment,
    `value` varchar(254) not null default '',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8mb4_bin;;

insert into `test` (`value`)
values ('test');
