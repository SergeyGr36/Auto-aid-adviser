insert into adviser_usr(email, password, role, active, activation_code) values
('testuser1@mail.com', '$2a$10$pj9f.rW8PsE734kW5xLsWuvnKyZn/wn.9bs2P2Fsv/lj2u5E4Zkb.', 'ROLE_USER', true, null),
('testuser2@mail.com', '$2a$10$pj9f.rW8PsE734kW5xLsWuvnKyZn/wn.9bs2P2Fsv/lj2u5E4Zkb.', 'ROLE_USER', false, 'asdf-1234-simple'),
('testbusiness1@mail.com', '$2a$10$pj9f.rW8PsE734kW5xLsWuvnKyZn/wn.9bs2P2Fsv/lj2u5E4Zkb.', 'ROLE_BUSINESS', true, null),
('testbusiness2@mail.com', '$2a$10$pj9f.rW8PsE734kW5xLsWuvnKyZn/wn.9bs2P2Fsv/lj2u5E4Zkb.', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'testuser1@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'testuser2@mail.com'));

insert into business_usr(user_details_id) values
((select id from adviser_usr where email like 'testbusiness1@mail.com')),
((select id from adviser_usr where email like 'testbusiness2@mail.com'));

insert into business_type(name) values
('CTO'),
('shinomantazh'),
('shop'),
('test');

insert into service_type(name, business_type_id) values
('body', (select id from business_type where name like 'CTO')),
('run', (select id from business_type where name like 'CTO')),
('engine', (select id from business_type where name like 'CTO')),
('gum', (select id from business_type where name like 'shinomantazh')),
('disk', (select id from business_type where name like 'shinomantazh')),
('test', (select id from business_type where name like 'shinomantazh'));

insert into service(name, service_type_id) values
('straightening dents 1', (select id from service_type where name like 'body')),
('balancing 1', (select id from service_type where name like 'run')),
('oil change 1', (select id from service_type where name like 'engine')),
('straightening 1', (select id from service_type where name like 'disk')),
('straightening dents 2', (select id from service_type where name like 'body')),
('balancing 2', (select id from service_type where name like 'run')),
('oil change 2', (select id from service_type where name like 'engine')),
('straightening 2', (select id from service_type where name like 'disk')),
('straightening dents 3', (select id from service_type where name like 'body')),
('balancing 3', (select id from service_type where name like 'run')),
('oil change 3', (select id from service_type where name like 'engine')),
('straightening 3', (select id from service_type where name like 'disk')),
('straightening dents 4', (select id from service_type where name like 'body')),
('balancing 4', (select id from service_type where name like 'run')),
('oil change 4', (select id from service_type where name like 'engine')),
('straightening 4', (select id from service_type where name like 'disk')),
('straightening dents 5', (select id from service_type where name like 'body')),
('balancing 5', (select id from service_type where name like 'run')),
('oil change 5', (select id from service_type where name like 'engine')),
('straightening 5', (select id from service_type where name like 'disk')),
('straightening dents 6', (select id from service_type where name like 'body')),
('balancing 6', (select id from service_type where name like 'run')),
('oil change 6', (select id from service_type where name like 'engine')),
('straightening 6', (select id from service_type where name like 'disk')),
('straightening dents 7', (select id from service_type where name like 'body')),
('balancing 7', (select id from service_type where name like 'run')),
('oil change 7', (select id from service_type where name like 'engine')),
('straightening 7', (select id from service_type where name like 'disk')),
('straightening dents 8', (select id from service_type where name like 'body')),
('balancing 8', (select id from service_type where name like 'run')),
('oil change 8', (select id from service_type where name like 'engine')),
('straightening 8', (select id from service_type where name like 'disk')),
('rubber change', (select id from service_type where name like 'gum'));

insert into business(phone, address, latitude, longitude, name, business_user_user_details_id) values
('098-123-45-67', 'Kiev', 100, 100, 'user 1 STO 1', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness1@mail.com')),
('098-123-45-67', 'Kiev', 101, 110, 'user 1 STO 2', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness1@mail.com')),
('066-666-66-66', 'Kiev', 102, 120, 'user 2 STO 1', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness2@mail.com')),
('096-999-99-99', 'Kiev', 103, 130, 'user 2 STO 2', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness2@mail.com'));

insert into work_time(day, from_time, to_time, business_id) values
(0, now(), now(), (select id from business limit 1)),
(1, now(), now(), (select id from business limit 1)),
(2, now(), now(), (select id from business limit 1));

insert into business_has_service(business_id, service_for_businesses_id)
select b.id, s.id from business b, service s;

insert into service(name, service_type_id) values
('for-delete-test', (select id from service_type where name like 'disk'));