insert into business_type(name) values
('CTO'),
('shinomantazh'),
('shop'),
('test');

insert into service_type(name, business_type_id) values
('body', (select id from business_type where name like 'CTO')),
('run', (select id from business_type where name like 'CTO')),
('engine', (select id from business_type where name like 'CTO')),
('disk', (select id from business_type where name like 'shinomantazh')),
('test', (select id from business_type where name like 'shinomantazh')),
('gum', (select id from business_type where name like 'shinomantazh'));

insert into service(name, service_type_id) values
('straightening dents', (select id from service_type where name like 'body')),
('balancing', (select id from service_type where name like 'run')),
('oil change', (select id from service_type where name like 'engine')),
('straightening discs', (select id from service_type where name like 'disk')),
('rubber change', (select id from service_type where name like 'gum'));

insert into business(phone, address, latitude, longitude, name, working_days, working_hours, business_user_user_details_id) values
('098-123-45-67', 'Kiev', 100, 100, 'user 1 STO 1', null, null, (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'bvg@mail.com')),
('098-123-45-67', 'Kiev', 101, 110, 'user 1 STO 2', null, null, (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'bvg@mail.com')),
('066-666-66-66', 'Kharkov', 102, 120, 'user 2 STO 1', null, null, (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'bkc@mail.com')),
('096-999-99-99', 'Kharkov', 103, 130, 'user 2 STO 2', null, null, (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'bkc@mail.com'));

insert into business_has_service(business_id, service_for_businesses_id)
select b.id, s.id from business b, service s;

insert into service(name, service_type_id) values
('for-delete-test', (select id from service_type where name like 'disk'));
