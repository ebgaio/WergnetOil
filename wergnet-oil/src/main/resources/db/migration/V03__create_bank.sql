Create table  bank (
	id_bank bigint primary key auto_increment,
	name varchar(30) not null,
    number bigint not null,
    active boolean not null
)engine=InnoDB default charset=UTF8MB4;

insert into bank (name, number, active) values("AIB", 100, true);
insert into bank (name, number, active) values("Ulster Bank", 200, true);
insert into bank (name, number, active) values("BOI", 140, true);
insert into bank (name, number, active) values("M26", 180, true);
