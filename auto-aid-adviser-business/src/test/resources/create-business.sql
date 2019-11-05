insert into business_type(type) values
('CTO'),
('shinomantazh'),
('shop'),
('gaz');

insert into service_type(type, type_business_id) values
('body', (select id from business_type where type like 'CTO')),
('run', (select id from business_type where type like 'CTO')),
('engine', (select id from business_type where type like 'CTO')),
('disk', (select id from business_type where type like 'shinomantazh')),
('gum', (select id from business_type where type like 'shinomantazh'));

insert into service(type, type_service_id) values
('straightening dents', (select id from service_type where type like 'body')),
('balancing', (select id from service_type where type like 'run')),
('oil change', (select id from service_type where type like 'engine')),
('straightening discs', (select id from service_type where type like 'disk')),
('rubber change', (select id from service_type where type like 'gum'));

insert into business(phone, address, latitude, longitude, name, working_days, working_hours, business_user_user_details_id) values
('098-123-45-67', 'Kiev', 100, 100, 'My first STO', null, null, (select user_details_id from business_usr limit 1)),
('067-876-32-10', 'Kharkov', 90, 100, 'My second STO', null, null, (select user_details_id from business_usr limit 1));

insert into business_has_service(business_id, service_businesses_id)
select b.id, s.id from business b, service s;