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
('СТО'),
('шиномонтах'),
('магазин'),
('test');

insert into service_type(name, business_type_id) values
('Техническое обслуживание', (select id from business_type where name like 'СТО')),
('Диагностика автомобиля', (select id from business_type where name like 'СТО')),
('Ремонт ходовой части', (select id from business_type where name like 'СТО')),
('Ремонт автоэлектрики', (select id from business_type where name like 'СТО')),
('шины', (select id from business_type where name like 'шиномонтах')),
('диски', (select id from business_type where name like 'шиномонтах')),
('test', (select id from business_type where name like 'шиномонтах'));

insert into service(name, service_type_id) values
('Замена моторного масла', (select id from service_type where name like 'Техническое обслуживание')),
('Снятие верхней защиты двигателя', (select id from service_type where name like 'Техническое обслуживание')),
('Замена воздушного фильтра', (select id from service_type where name like 'Техническое обслуживание')),
('Замена фильтра салона в бардачке', (select id from service_type where name like 'Техническое обслуживание')),
('Замена фильтра салона через педальный узел', (select id from service_type where name like 'Техническое обслуживание')),
('Замена топливного фильтра', (select id from service_type where name like 'Техническое обслуживание')),
('Замена топливного фильтра в бензобаке', (select id from service_type where name like 'Техническое обслуживание')),

('Диагностика ходовой части', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика двигателя (ремни, подтекания)', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика толщины лако-красочного покрытия', (select id from service_type where name like 'Диагностика автомобиля')),
('Комплексная диагностика (ходовая, двигатель, краска, компьютер)', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика электрооборудования', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика аккумулятора', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика впуска дымогенератором', (select id from service_type where name like 'Диагностика автомобиля')),
('Диагностика дымогенератором выхлопной системы', (select id from service_type where name like 'Диагностика автомобиля')),

('Замена стойки стабилизатора', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена задней балки', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена пружины', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена маятника', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена рулевого наконечника', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена рулевой тяги', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена шаровой опоры запрессованной', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена шаровой опоры (болты)', (select id from service_type where name like 'Ремонт ходовой части')),
('Замена полуоси', (select id from service_type where name like 'Ремонт ходовой части')),

('Компьютерная диагностика', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Зарядка аккумуляторной батареи', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Ремонт электропроводки (нормочас)', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Адаптация роботизированной КПП', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Замена аккумулятора', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Обнуление сервисного интервала', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Замена стартера', (select id from service_type where name like 'Ремонт автоэлектрики')),
('Замена генератора', (select id from service_type where name like 'Ремонт автоэлектрики')),

('рихтовка', (select id from service_type where name like 'диски')),
('зачистка', (select id from service_type where name like 'диски')),
('прокатка', (select id from service_type where name like 'диски')),
('сварка', (select id from service_type where name like 'диски')),
('сезонная замена', (select id from service_type where name like 'шины'));

insert into business(phone, address, latitude, longitude, name, business_user_user_details_id) values
('098-123-45-67', 'Киев', 50.44, 30.53 , 'СТО Дарница', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness1@mail.com')),
('098-123-45-67', 'вулиця Кіровоградська, Петропавлівська Борщагівка, Київська обл.', 50.42, 30.33, 'СТО АвтоХелп', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness1@mail.com')),
('066-666-66-66', 'Киев', 50.42, 30.80, 'user 2 STO 1', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness2@mail.com')),
('096-999-99-99', 'Киев', 50.50, 30.30, 'user 2 STO 2', (select id from adviser_usr a inner join business_usr b on (a.id = b.user_details_id) where a.email like 'testbusiness2@mail.com'));

insert into work_time(day, from_time, to_time, business_id) values
(0, now(), now(), (select id from business limit 1)),
(1, now(), now(), (select id from business limit 1)),
(2, now(), now(), (select id from business limit 1));

insert into business_has_service(business_id, service_for_businesses_id)
select b.id, s.id from business b, service s;

insert into service(name, service_type_id) values
('for-delete-test', (select id from service_type where name like 'disk'));

insert into type_car(name) values ('Легковой'), ('Грузовой'), ('Электрокар'), ('Мотоцикл');

insert into car_brand(name) values ('Audi'), ('BMW'), ('Mercedes'), ('ВАЗ'), ('ЗАЗ'), ('Toyota'), ('Mitsubishi');

insert into car_model(name, car_brand_id, type_car_id) values
('YT202-E', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'Грузовой')),
('i3', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'Электрокар')),
('X6M', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'Электрокар')),
('X5', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'Легковой')),
('M5', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'Легковой')),
('A4', (select id from car_brand where name like 'Audi'), (select id from type_car where name like 'Легковой')),
('TT', (select id from car_brand where name like 'Audi'), (select id from type_car where name like 'Легковой')),
('RAV4', (select id from car_brand where name like 'Toyota'), (select id from type_car where name like 'Легковой')),
('C-HR', (select id from car_brand where name like 'Toyota'), (select id from type_car where name like 'Легковой'))
;

-- user cars

insert into user_car(release_year, car_model_id, simple_user_user_details_id) values
(
    '2015',
    (select id from car_model where name like 'TT'),
    (select user_details_id from simple_usr where first_name in('Vasya'))
),

(
    '2016',
    (select id from car_model where name like 'X5'),
    (select user_details_id from simple_usr where first_name in('Vasya'))
);

-- add feedback for business

insert into feedback(text, rating, business_id, user_id, create_date) values
(
    'Good service. Thanks!',
    5,
    (select id from business where name like 'СТО Дарница'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'Хороший сервис!!!',
    5,
    (select id from business where name like 'СТО Дарница'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'Не понравилось(((. Имел горький опыт кузовного ремонта на данном СТО. Уж не знаю где я так нагрешил.',
    2,
    (select id from business where name like 'СТО Дарница'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'So so',
    3,
    (select id from business where name like 'СТО Дарница'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
);