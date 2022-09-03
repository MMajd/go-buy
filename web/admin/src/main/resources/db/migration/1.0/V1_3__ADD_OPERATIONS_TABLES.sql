CREATE TABLE operations (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name varchar(128) NOT NULL,
    PRIMARY KEY PK_OP_ID (id),
    CONSTRAINT UNIQUE name_UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE roles_operations (
    role_id bigint(20) NOT NULL,
    op_id bigint(20) NOT NULL,
    PRIMARY KEY PK_ROLE_OPS (role_id, op_id),
    KEY FK_ROLES_OPS_ROLE_ID (role_id),
    KEY FK_ROLES_OPS_OP_ID (op_id),
    CONSTRAINT FK_ROLES_OPS_ROLE_ID FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT FK_ROLES_OPS_OP_ID FOREIGN KEY (op_id) REFERENCES operations (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- SEEDING OPERATIONS TABLE
insert into operations (name) values ('can_view_products');
insert into operations (name) values ('can_delete_products');
insert into operations (name) values ('can_update_products');
insert into operations (name) values ('can_create_products');

insert into operations (name) values ('can_view_users');
insert into operations (name) values ('can_delete_users');
insert into operations (name) values ('can_update_users');
insert into operations (name) values ('can_create_users');

insert into operations (name) values ('can_view_categories');
insert into operations (name) values ('can_delete_categories');
insert into operations (name) values ('can_update_categories');
insert into operations (name) values ('can_create_categories');

insert into operations (name) values ('can_view_brands');
insert into operations (name) values ('can_delete_brands');
insert into operations (name) values ('can_update_brands');
insert into operations (name) values ('can_create_brands');

insert into operations (name) values ('can_view_orders');
insert into operations (name) values ('can_delete_orders');
insert into operations (name) values ('can_update_orders');
insert into operations (name) values ('can_create_orders');

insert into operations (name) values ('can_view_questions');
insert into operations (name) values ('can_delete_questions');
insert into operations (name) values ('can_update_questions');
insert into operations (name) values ('can_create_questions');

insert into operations (name) values ('can_view_reviews');
insert into operations (name) values ('can_delete_reviews');
insert into operations (name) values ('can_update_reviews');
insert into operations (name) values ('can_create_reviews');
