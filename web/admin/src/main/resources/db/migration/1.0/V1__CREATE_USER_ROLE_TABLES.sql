CREATE TABLE users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    first_name varchar(40) NOT NULL,
    last_name varchar(40) NOT NULL,
    email varchar(128) NOT NULL,
    password varchar(64) NOT NULL,
    photos varchar(64) DEFAULT NULL,
    enabled bit(1) DEFAULT NULL,

    PRIMARY KEY PK_USER_ID (`id`),
    UNIQUE email_UNIQUE (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE roles (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(40) NOT NULL,
    description varchar(255) NOT NULL,

    PRIMARY KEY PK_ROLES_ID (`id`),
    UNIQUE name_UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE users_roles (
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    PRIMARY KEY PK_USERS_ROLES (user_id, role_id),
    KEY FK_ROLE_ID (role_id),
    KEY FK_USER_ID (user_id),
    CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

