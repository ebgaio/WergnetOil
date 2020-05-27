create table customer (
	id_customer bigint primary key auto_increment,
	name_customer varchar(30) not null,
	last_name varchar(30) not null,
	active boolean not null,
	email varchar(50) not null,
	phone varchar(30),
    street1 VARCHAR(30),
    street2 VARCHAR(30),
    county VARCHAR(30),
    city VARCHAR(30),
    eir_code VARCHAR(30),
    country VARCHAR(30)
)engine=InnoDB default charset=UTF8MB4;

insert into customer (name_customer, last_name, active, email, phone, street1, street2, county, city, eir_code, country) values ('Evandro', 'Gaio', true, 'evandro@gmail.com', '3583125066', "5 OConnel", "Centre", "Dublin", "Dublin", "D6W", "Ireland");
insert into customer (name_customer, last_name, active, email, phone, street1, street2, county, city, eir_code, country) values ('Andressa', 'Finotti', true, 'andressa@gmail.com', '553732164272', "5 Port", "Donegal", "Dublin", "Dublin", "D6W", "Ireland");
insert into customer (name_customer, last_name, active, email, phone, street1, street2, county, city, eir_code, country) values ('Ana', 'Gois', true, 'ana@uol.com.br', '553432456874', "5 Tirol", "Clonsila", "Morris", "Dublin", "D2", "Ireland");
insert into customer (name_customer, last_name, active, email, phone, street1, street2, county, city, eir_code, country) values ('Paula', 'Mendes', true, 'paula@pop.com.br', '551132654782', "5 Lest", "Centre", "Dublin", "Dublin", "D3", "Ireland");
insert into customer (name_customer, last_name, active, email, phone, street1, street2, county, city, eir_code, country) values ('Oto', 'Costa', true, 'oto@ibm.com', '553136548521', "5 Kirney", "Centre", "Dublin", "Dublin", "D2", "Ireland");

