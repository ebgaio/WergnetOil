CREATE TABLE userCard (
	id BIGINT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE permission (
	id BIGINT PRIMARY KEY,
	description VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE userCard_permission (
	id_userCard BIGINT NOT NULL,
	id_permission BIGINT NOT NULL,
	PRIMARY KEY (id_userCard, id_permission),
	FOREIGN KEY (id_userCard) REFERENCES userCard(id),
	FOREIGN KEY (id_permission) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO userCard (id, name, email, password) values (1, 'Administrador', 'admin@wergnetoil.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO userCard (id, name, email, password) values (2, 'Maria Silva', 'maria@wergnetoil.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permission (id, description) values (1, 'ROLE_REGISTER_CARD');
INSERT INTO permission (id, description) values (2, 'ROLE_REMOVE_CARD');
INSERT INTO permission (id, description) values (3, 'ROLE_SEARCH_CARD');

INSERT INTO permission (id, description) values (4, 'ROLE_REGISTER_CUSTOMER');
INSERT INTO permission (id, description) values (5, 'ROLE_REMOVE_CUSTOMER');
INSERT INTO permission (id, description) values (6, 'ROLE_SEARCH_CUSTOMER');

INSERT INTO permission (id, description) values (7, 'ROLE_REGISTER_TRANSACTION');
INSERT INTO permission (id, description) values (8, 'ROLE_REMOVE_TRANSACTION');
INSERT INTO permission (id, description) values (9, 'ROLE_SEARCH_TRANSACTION');

INSERT INTO permission (id, description) values (10, 'ROLE_REGISTER_BANK');
INSERT INTO permission (id, description) values (11, 'ROLE_REMOVE_BANK');
INSERT INTO permission (id, description) values (12, 'ROLE_SEARCH_BANK');

-- admin
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 1);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 2);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 3);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 4);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 5);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 6);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 7);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 8);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 9);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 10);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 11);
INSERT INTO userCard_permission (id_userCard, id_permission) values (1, 12);

-- maria
INSERT INTO userCard_permission (id_userCard, id_permission) values (2, 3);
INSERT INTO userCard_permission (id_userCard, id_permission) values (2, 6);
INSERT INTO userCard_permission (id_userCard, id_permission) values (2, 9);
INSERT INTO userCard_permission (id_userCard, id_permission) values (2, 12);
