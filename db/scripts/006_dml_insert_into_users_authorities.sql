insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$qgbhYS8z0ZBWnhKGUmi/X.BOvgvHewInYReFMZfXsaPnnC7nuzRJ6',
(select id from authorities where authority = 'ROLE_ADMIN'));
