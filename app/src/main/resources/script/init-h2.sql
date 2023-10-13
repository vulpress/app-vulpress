drop
    all objects;

drop table if exists account_user;
create table if not exists account_user
(
    id        serial     not null primary key,
    username  text       not null,                 -- username used for authentication
    mail_addr text       not null default '',      -- email address
    user_pw   text       not null,                 -- encrypted password
    user_role varchar(5) not null default 'PLAIN', -- possible values: PLAIN, ADMIN
    inactive  boolean    not null default false,
    reg_token uuid,                                -- token for registration and password recovery
    token_exp timestamp without time zone          -- expiration timestamp for the token, if any
);

insert into account_user (username, user_pw, user_role)
values ('admin', '$2a$10$H01N158v4Ac/Tj5h.J3JHOQELFjQ1qXAXDtahZBMFdk/UtG6eVNTy', 'ADMIN'); -- admin

insert into account_user (username, user_pw, user_role)
values ('dev', '$2a$10$H01N158v4Ac/Tj5h.J3JHOQELFjQ1qXAXDtahZBMFdk/UtG6eVNTy', 'PLAIN'); -- admin


alter table if exists paragraph
    drop constraint if exists fk_paragraph_article;

alter table if exists article_tag
    drop constraint if exists fk_tag_article;

drop index if exists article_title_idx;
drop table if exists article;
create table if not exists article
(
    id               serial       not null primary key,
    norm_title       varchar(100) not null,
    title            text         not null       default '',           -- the title of the article
    author_name      text         not null       default '',           -- the author's name; especially important if the author is not a user
    author_id        integer,                                          -- if NULL, the author is not an application user
    description      text         not null       default '',           -- short description usable for previews
    created_at       timestamp without time zone default now(),
    issue_date       date                        default current_date, -- if the article is relevant for a specific date (e.g. a Christmas speech) this denotes that date,
    published        boolean                     default false,
    image            uuid,                                             -- the UUID of the cover image
    content_category integer      not null       default 0
);
create unique index if not exists article_title_idx on article (norm_title);

drop table if exists paragaph;
create table if not exists paragraph
(
    id          serial  not null primary key,
    article     integer not null,
    article_key integer not null,
    type        text    not null default 'TEXT', -- [ 'TITLE', 'ILLUSTRATION', 'TEXT' ]
    content     text    not null default '',     -- the text content for TITLE and TEXT types
    image       uuid                             -- the image UUID for ILLUSTRATION type
);
alter table paragraph
    add constraint fk_paragraph_article foreign key (article) references article (id);

drop table if exists article_tag;
create table if not exists article_tag
(
    id      serial  not null primary key,
    article integer not null,
    tag_    text    not null default ''
);
alter table article_tag
    add constraint fk_tag_article
        foreign key (article) references article (id);

drop index if exists category_title_idx;
drop table if exists content_category;
create table if not exists content_category
(
    id          serial                      not null primary key,
    norm_title  varchar(100)                not null,
    title       text                        not null default '',
    description text                        not null default '',
    built_in    boolean                     not null default false,
    public_vis  boolean                     not null default false,
    image       uuid,
    created_at  timestamp without time zone not null default now()
);
create unique index if not exists category_title_idx on content_category (norm_title);

insert into content_category (norm_title, title, description, built_in, public_vis)
values ('sys_archive', 'Archive', 'Category for deleted elements', true, false),
       ('sys_sandbox', 'Sandbox', 'Category for not yet published elements', true, false),
       ('homilies', 'Homilies', 'Category for homilies', true, true),
       ('articles', 'Articles', 'Category for generic articles', true, true);


create table if not exists tag_entry
(
    tag_name varchar(255) not null primary key
);

create table if not exists tag_alias
(
    id         serial       not null primary key,
    tag_entry  varchar(255) not null,
    tag_value  text         not null default '',
    is_primary boolean      not null default false
);
alter table tag_alias
    add constraint fk_alias_tag
        foreign key (tag_entry) references tag_entry (tag_name);
