CREATE TABLE TRANSACTION (
    id_transaction BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    value_Transaction DECIMAL(10.2) NOT NULL,
   	date_Credit DATE,
	date_Debit DATE,
	customer_id BIGINT NOT NULL,
   	KEY customer_id (customer_id),
   	bank_id BIGINT NOT NULL,
   	KEY bank_id (bank_id),
    CONSTRAINT customer_transaction_fk FOREIGN KEY (customer_id)
    REFERENCES customer (id_customer),
    CONSTRAINT bank_transaction_fk FOREIGN KEY (bank_id)
    REFERENCES bank (id_bank)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO TRANSACTION (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) VALUES("Today is a beautiful day.", 16234.0, '2017-06-10', '2017-11-21', 1, 1);
INSERT INTO TRANSACTION (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) VALUES("I love you so much", 1240.0, '2017-02-20', '2017-12-31', 2, 2);
INSERT INTO TRANSACTION (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) VALUES("You is love of my live", 340.0, '2018-10-17', '2018-12-24', 1, 3);
INSERT INTO TRANSACTION (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) VALUES("I can do this.", 50.0, '2016-06-29', '2017-02-24', 2, 4);
INSERT INTO TRANSACTION (description, value_Transaction, date_Credit, date_Debit, customer_id, bank_id) VALUES("You is the best.", 1850.0, '2017-09-26', '2017-12-20', 3, 3);
