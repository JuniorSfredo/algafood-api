create table group_has_permission (
    group_id bigint not null,
    permission_id bigint not null
) engine=InnoDB;

create table restaurant_has_payment_method (
    payment_method_id bigint not null,
    restaurant_id bigint not null
) engine=InnoDB;

create table tb_city (
    id bigint not null auto_increment,
    state_id bigint not null, name varchar(100) not null,

    primary key (id)
) engine=InnoDB;

create table tb_group (
    id bigint not null auto_increment,
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB;

create table tb_kitchen (
    id bigint not null auto_increment,
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB;

create table tb_payment_method (
    id bigint not null auto_increment,
    description varchar(80) not null,

    primary key (id)
) engine=InnoDB;

create table tb_permission (
    id bigint not null auto_increment,
    description varchar(150) not null,
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB;

create table tb_product (
    is_active bit not null,
    price float(10) not null,
    id bigint not null auto_increment,
    restaurant_id bigint,
    description varchar(150) not null,
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB;

create table tb_restaurant (
    address_city_id bigint not null,
    delivery_fee decimal(38,2),
    id bigint not null auto_increment,
    created_at datetime not null,
    updated_at datetime not null,
    kitchen_id bigint not null,
    address_cep varchar(25),
    address_complement varchar(100),
    address_neighborhood varchar(100),
    address_street_name varchar(100),
    address_street_number varchar(150),
    name varchar(80) not null,

    primary key (id)
) engine=InnoDB;
create table tb_state (
    id bigint not null auto_increment,
    name varchar(80) not null,
    primary key (id)
) engine=InnoDB;

create table tb_user (
    created_at datetime(6) not null,
    id bigint not null auto_increment,
    email varchar(200) not null,
    name varchar(80) not null,
    password varchar(50) not null,

    primary key (id)
) engine=InnoDB;

create table tb_user_has_group (
    group_id bigint not null,
    user_id bigint not null
) engine=InnoDB;

alter table group_has_permission add constraint fk_group_has_permission_permission_id foreign key (permission_id) references tb_permission (id);
alter table group_has_permission add constraint fk_group_has_permission_group_id foreign key (group_id) references tb_group (id);
alter table restaurant_has_payment_method add constraint fk_restaurant_has_payment_method_payment_method_id foreign key (payment_method_id) references tb_payment_method (id);
alter table restaurant_has_payment_method add constraint fk_restaurant_has_payment_method_restaurant_id foreign key (restaurant_id) references tb_restaurant (id);
alter table tb_city add constraint fk_city_state_id foreign key (state_id) references tb_state (id);
alter table tb_product add constraint fk_product_restaurant_id foreign key (restaurant_id) references tb_restaurant (id);
alter table tb_restaurant add constraint fk_restaurant_address_city_id foreign key (address_city_id) references tb_city (id);
alter table tb_restaurant add constraint fk_restaurant_kitchen_id foreign key (kitchen_id) references tb_kitchen (id);
alter table tb_user_has_group add constraint fk_user_group_group_id foreign key (group_id) references tb_group (id);
alter table tb_user_has_group add constraint fk_user_group_user_id foreign key (user_id) references tb_user (id);