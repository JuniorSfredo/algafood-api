SET foreign_key_checks = 0;

DELETE FROM tb_restaurant;
DELETE FROM tb_city;
DELETE FROM tb_state;
DELETE FROM tb_payment_method;
DELETE FROM tb_permission;
DELETE FROM tb_group;
DELETE FROM group_has_permission;
DELETE FROM restaurant_has_payment_method;
DELETE FROM tb_kitchen;
DELETE FROM tb_user;
DELETE FROM tb_product;
DELETE FROM tb_user_has_group;

SET foreign_key_checks = 1;

ALTER TABLE tb_restaurant AUTO_INCREMENT = 1;
ALTER TABLE tb_city AUTO_INCREMENT = 1;
ALTER TABLE tb_state AUTO_INCREMENT = 1;
ALTER TABLE tb_payment_method AUTO_INCREMENT = 1;
ALTER TABLE tb_permission AUTO_INCREMENT = 1;
ALTER TABLE tb_group AUTO_INCREMENT = 1;
ALTER TABLE group_has_permission AUTO_INCREMENT = 1;
ALTER TABLE restaurant_has_payment_method AUTO_INCREMENT = 1;
ALTER TABLE tb_kitchen AUTO_INCREMENT = 1;
ALTER TABLE tb_user AUTO_INCREMENT = 1;
ALTER TABLE tb_product AUTO_INCREMENT = 1;
ALTER TABLE tb_user_has_group AUTO_INCREMENT = 1;

insert into tb_kitchen (id, name) values (1, 'Tailandesa');
insert into tb_kitchen (id, name) values (2, 'Indiana');
insert into tb_kitchen (id, name) values (3, 'Argentina');
insert into tb_kitchen (id, name) values (4, 'Brasileira');

insert into tb_state (id, name) values (1, 'Minas Gerais');
insert into tb_state (id, name) values (2, 'São Paulo');
insert into tb_state (id, name) values (3, 'Ceará');

insert into tb_city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into tb_city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into tb_city (id, name, state_id) values (3, 'São Paulo', 2);
insert into tb_city (id, name, state_id) values (4, 'Campinas', 2);
insert into tb_city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id, address_cep, address_street_name, address_street_number, address_neighborhood) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp , 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, 3);
insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, 3);
insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, 4);
insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, 5);
insert into tb_restaurant (id, name, delivery_fee, kitchen_id, created_at, updated_at, address_city_id) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, 1);

insert into tb_payment_method (id, description) values (1, 'Cartão de crédito');
insert into tb_payment_method (id, description) values (2, 'Cartão de débito');
insert into tb_payment_method (id, description) values (3, 'Dinheiro');