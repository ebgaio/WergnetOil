create table transaction (
    id_transaction bigint primary key auto_increment,
    description varchar(50) not null,
    balance_card decimal(10.2) not null,
	customer_id bigint not null,
   	bank_id bigint not null,
    CONSTRAINT transaction_fk foreign key (customer_id)
    references customer (id_customer),
    CONSTRAINT bank_transaction_fk foreign key (bank_id)
    references bank (id_bank)
)engine=InnoDB default charset=UTF8MB4;

insert into transaction (description, balance_Card, customer_id, bank_id) values("Today is a beautifull day.", 16234.0, 1, 1);
insert into transaction (description, balance_Card, customer_id, bank_id) values("I love you so much", 1240.0, 2, 2);
insert into transaction (description, balance_Card, customer_id, bank_id) values("You is a love of mmy live", 340.0, 1, 3);
insert into transaction (description, balance_Card, customer_id, bank_id) values("I can do this.", 50.0, 2, 4);
insert into transaction (description, balance_Card, customer_id, bank_id) values("You is the best.", 1850.0, 3, 3);