insert into business_type(name) values
('CTO'),
('shinomantazh'),
('shop'),
('test');

insert into service_type(name, business_type_id) values
('body', (select id from business_type where name like 'CTO')),
('run', (select id from business_type where name like 'CTO')),
('engine', (select id from business_type where name like 'CTO')),
('runnew', (select id from business_type where name like 'shinomantazh')),
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
('098-123-45-67', 'Kiev', 100, 100, 'My first STO', null, null, (select user_details_id from business_usr limit 1)),
('067-876-32-10', 'Kharkov', 90, 100, 'My second STO', null, null, (select user_details_id from business_usr limit 1));

insert into business_has_service(business_id, service_for_businesses_id)
select b.id, s.id from business b, service s;

insert into service(name, service_type_id) values
('for-delete-test', (select id from service_type where name like 'disk'));
