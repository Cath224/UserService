create schema if not exists ateupeonding_user;


create table if not exists ateupeonding_user.user
(
    id                uuid default gen_random_uuid() primary key,
    login             text not null unique,
    password          text not null,
    created_timestamp timestamp with time zone
);

create table if not exists ateupeonding_user.seed
(
    id uuid default gen_random_uuid() primary key,
    reference_id text not null unique,
    value text not null,
    created_timestamp timestamp with time zone
)