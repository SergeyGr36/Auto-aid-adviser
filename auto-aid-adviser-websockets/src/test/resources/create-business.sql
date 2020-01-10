insert into business_type(name) values
('CTO'),
('shinomantazh'),
('магазин'),
('магазин2'),
('шиномонтаж'),
('якасьхня'),
('shop'),
('test');

insert into service_type(name, business_type_id) values
('body', (select id from business_type where name like 'CTO')),
('run', (select id from business_type where name like 'CTO')),
('kjsdhkf', (select id from business_type where name like 'CTO')),
('engine', (select id from business_type where name like 'CTO')),
('двигун', (select id from business_type where name like 'шиномонтаж')),
('двері', (select id from business_type where name like 'магазин')),
('runnew', (select id from business_type where name like 'shinomantazh')),
('disk', (select id from business_type where name like 'shinomantazh')),
('test', (select id from business_type where name like 'shinomantazh')),
('gum', (select id from business_type where name like 'shinomantazh'));

insert into service(name, service_type_id) values
('straightening dents', (select id from service_type where name like 'body')),
('balancing', (select id from service_type where name like 'run')),
('oil change', (select id from service_type where name like 'engine')),
('straightening discs', (select id from service_type where name like 'disk')),
('rubber change', (select id from service_type where name like 'gum')),
('заміна масла', (select id from service_type where name like 'двигун'));