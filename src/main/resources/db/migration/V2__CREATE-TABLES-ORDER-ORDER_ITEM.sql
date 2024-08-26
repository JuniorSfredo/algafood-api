CREATE TABLE tb_order (
    id bigint auto_increment,
    subtotal decimal(10, 2) not null,
    delivery_fee decimal(10, 2) not null,
    total_value decimal(10, 2) not null,

    restaurant_id bigint not null,
    user_client_id bigint not null,
    payment_method_id bigint not null,

    address_city_id bigint not null,
    address_cep varchar(25) not null,
    address_complement varchar(100),
    address_neighborhood varchar(100),
    address_street_name varchar(100),
    address_street_number varchar(150),

    status varchar(10) not null,

    created_at datetime not null,
    confirmation_date datetime,
    cancellation_date datetime,
    delivery_date datetime,

    primary key (id),

    constraint fk_order_address_city foreign key (address_city_id) references tb_city (id),
    constraint fk_user_client_id foreign key (user_client_id) references tb_user (id),
    constraint fk_restaurant_id foreign key (restaurant_id) references tb_restaurant(id),
    constraint fk_payment_method_id foreign key (payment_method_id) references tb_payment_method (id)
) engine=InnoDB default charset = utf8mb4;

create table tb_order_item (
    id bigint auto_increment,
    quantity smallint not null,
    price decimal(10, 2) not null,
    total_price decimal (10, 2) not null,
    observation varchar(150) null,

    order_id bigint not null,
    product_id bigint not null,

    primary key (id),
    unique key uk_item_order_product (order_id, product_id),

    constraint fk_order_id foreign key (order_id) references tb_order (id),
    constraint fk_product_id foreign key (product_id) references tb_product (id)
) engine=InnoDB default charset = utf8mb4;