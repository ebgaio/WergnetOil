CREATE TABLE USERCARD (
	id BIGINT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE PERMISSION (
	id BIGINT PRIMARY KEY,
	description VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE USERCARD_PERMISSION (
	id_userCard BIGINT NOT NULL,
	id_permission BIGINT NOT NULL,
	PRIMARY KEY (id_userCard, id_permission),
	FOREIGN KEY (id_userCard) REFERENCES userCard(id),
	FOREIGN KEY (id_permission) REFERENCES permission(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO USERCARD (id, name, email, password) VALUES (1, 'Administrador', 'admin@wergnetoil.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO USERCARD (id, name, email, password) VALUES (2, 'Maria Silva', 'maria@wergnetoil.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO PERMISSION (id, description) VALUES (1, 'ROLE_REGISTER_CARD');
INSERT INTO PERMISSION (id, description) VALUES (2, 'ROLE_REMOVE_CARD');
INSERT INTO PERMISSION (id, description) VALUES (3, 'ROLE_SEARCH_CARD');

INSERT INTO PERMISSION (id, description) VALUES (4, 'ROLE_REGISTER_CUSTOMER');
INSERT INTO PERMISSION (id, description) VALUES (5, 'ROLE_REMOVE_CUSTOMER');
INSERT INTO PERMISSION (id, description) VALUES (6, 'ROLE_SEARCH_CUSTOMER');

INSERT INTO PERMISSION (id, description) VALUES (7, 'ROLE_REGISTER_TRANSACTION');
INSERT INTO PERMISSION (id, description) VALUES (8, 'ROLE_REMOVE_TRANSACTION');
INSERT INTO PERMISSION (id, description) VALUES (9, 'ROLE_SEARCH_TRANSACTION');

INSERT INTO PERMISSION (id, description) VALUES (10, 'ROLE_REGISTER_BANK');
INSERT INTO PERMISSION (id, description) VALUES (11, 'ROLE_REMOVE_BANK');
INSERT INTO PERMISSION (id, description) VALUES (12, 'ROLE_SEARCH_BANK');

-- admin
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 1);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 2);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 3);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 4);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 5);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 6);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 7);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 8);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 9);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 10);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 11);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (1, 12);

-- maria
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (2, 3);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (2, 6);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (2, 9);
INSERT INTO USERCARD_PERMISSION (id_userCard, id_permission) VALUES (2, 12);
