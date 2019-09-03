create table country_codes (
    country_code_id varchar(3) not null,
    name varchar(20) not null,
    constraint pk_country_code primary key(country_code_id)
);

create table customers {
    customer_id bigint not null generated always as identity (start with 1, increment by 1),
    email varchar(20) not null,
    password varchar(20) not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    street varchar(20),
    city varchar(20),
    state_or_province varchar(20),
    postal_code varchar(20),
    country_code_id bigint,
    birthdate date,
    confirmation_code varchar(40),
    constraint pk_customer primary key(customer_id),
    constraint fk_to_country_codes foreign key(country_code_id) references country_codes(country_code_id)
}

create table customer_cards {
    customer_card_id bigint not null generated always as identity (start with 1, increment by 1),
    customer_id bigint not null,
    preferred boolean not null,
    card_number varchar(20) not null,
    expires date not null,
    ccv varchar(4),
    constraint pk_customer_card primary key(customer_card_id),
    constraint fk_to_customers foreign key(customer_id) references customers(customer_id)
}

create table payment_types {
    payment_type_id bigint not null generated always always as identity (start with 1, increment by 1),
    name varchar(20) not null,
    constraint pk_payment_type primary key(payment_type_id)
}

create table product_types {
    product_type_id bigint not null generated always always as identity (start with 1, increment by 1),
    name varchar(20) not null,
    constraint pk_product_type primary key(product_type_id)
}

create table products {
    product_id bigint not null generated always always as identity (start with 1, increment by 1),
    product_type_id bigint not null,
    name varchar(20) not null,
    price decimal(10, 2) not null,
    constraint pk_product primary key(product_id),
    constraint fk_product_types foreign key(product_type_id) references product_types(product_type_id)
}

create table sales_orders {
    sales_order_id bigint not null generated always always as identity (start with 1, increment by 1),
    order_date date not null,
    customer_id bigint not null,
    total decimal(10, 2),
    payment_type_id bigint,
    card_number varchar(20),
    card_expires date,
    filled date,
    constraint pk_sales_order primary key(sales_order_id),
    constraint fk_customers foreign key(customer_id) references customers(customer_id),
    constraint fk_payment_types foreign key(payment_types_id) references payment_types(payment_type_id)
}

create table sales_order_items {
    sales_order_item_id bigint not null generated always always as identity (start with 1, increment by 1),
    sales_order_id bigint not null,
    product_id bigint not null,
    quantity bigint not null,
    price decimal(10, 2) not null,
    constraint pk_sales_order_item primary key(sales_order_item_id),
    constraint fk_sales_orders foreign key(sales_order_id) references sales_orders(sales_order_id),
    constraint fk_products foreign key(product_id) references products(product_id)
}
