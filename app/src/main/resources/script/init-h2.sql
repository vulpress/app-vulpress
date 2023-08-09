drop table if exists account_user;

create table if not exists account_user (
    id        serial     not null primary key,
    username  text       not null, -- username used for authentication
    user_pw   text       not null, -- encrypted password
    user_role varchar(5) not null, -- possible values: PLAIN, ADMIN
    inactive  boolean    not null default false
);

insert into account_user (username, user_pw, user_role)
values ('admin', '$2a$10$H01N158v4Ac/Tj5h.J3JHOQELFjQ1qXAXDtahZBMFdk/UtG6eVNTy', 'ADMIN'); -- admin

insert into account_user (username, user_pw, user_role)
values ('dev', '$2a$10$H01N158v4Ac/Tj5h.J3JHOQELFjQ1qXAXDtahZBMFdk/UtG6eVNTy', 'PLAIN');   -- admin