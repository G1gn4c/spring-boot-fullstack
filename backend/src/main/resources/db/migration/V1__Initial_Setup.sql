create sequence customer_id_seq start with 1 increment by 1;

create table customer (
   age integer not null,
   id bigint not null,
   email varchar (255) not null,
   name varchar (255) not null,
   primary key (id),
   constraint customer_email_unique unique (email)
);