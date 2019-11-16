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