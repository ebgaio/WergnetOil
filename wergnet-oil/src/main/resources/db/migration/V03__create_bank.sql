CREATE TABLE BANK (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
    number BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

#Bank with id = 1, name = 'Default Bank', number = 0 and active = true, is used when value is unset.
INSERT INTO BANK (id, name, number, active) VALUES(1, 'Default Bank', 0, true);
INSERT INTO BANK (name, number, active) VALUES('AIB', 100, true);
INSERT INTO BANK (name, number, active) VALUES('Ulster Bank', 200, true);
INSERT INTO BANK (name, number, active) VALUES('BOI', 140, true);
INSERT INTO BANK (name, number, active) VALUES('M26', 180, true);
