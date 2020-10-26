create table transaction (
    id_transaction bigint primary key auto_increment,
    description varchar(50) not null,
    value_Transaction decimal(10.2) not null,
   	date_Credit DATE,
	date_Debit DATE,
	customer_id bigint not null,
   	KEY customer_id (customer_id),
   	bank_id bigint not null,
   	KEY bank_id (bank_id),
    CONSTRAINT customer_transaction_fk foreign key (customer_id)
    references customer (id_customer),
    CONSTRAINT bank_transaction_fk foreign key (bank_id)
    references bank (id_bank)
)engine=InnoDB default charset=UTF8MB4;

insert into transaction (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) values("Today is a beautiful day.", 16234.0, '2017-06-10', '2017-11-21', 1, 1);
insert into transaction (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) values("I love you so much", 1240.0, '2017-02-20', '2017-12-31', 2, 2);
insert into transaction (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) values("You is love of my live", 340.0, '2018-10-17', '2018-12-24', 1, 3);
insert into transaction (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) values("I can do this.", 50.0, '2016-06-29', '2017-02-24', 2, 4);
insert into transaction (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) values("You is the best.", 1850.0, '2017-09-26', '2017-12-20', 3, 3);
