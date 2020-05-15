create table Card (
	id_card bigint primary key auto_increment,
	card_number bigint not null,
	active boolean not null,
	balance decimal(10.2) not null,
	customer_id bigint not null,
    KEY customer_ind (customer_id),
    CONSTRAINT card_fk foreign key (customer_id)
    references customer (id_customer)
)engine=InnoDB default charset=UTF8MB4;

insert into card (card_number, active, balance, customer_id) values(9876, true, 100.0, 1);
insert into card (card_number, active, balance, customer_id) values(3547, true, 1400.0, 2);
insert into card (card_number, active, balance, customer_id) values(7532, true, 0.0, 3);
insert into card (card_number, active, balance, customer_id) values(9512, true, 530.0, 4);
insert into card (card_number, active, balance, customer_id) values(1234, true, 50.0, 5);
