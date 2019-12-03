insert into adviser_usr(email, password, role, active, activation_code) values
('svg@mail.com', '12345', 'ROLE_USER', true, null),
('skc@mail.com', '12345', 'ROLE_USER', false, 'asdf-1234-simple'),
('bvg@mail.com', '12345', 'ROLE_BUSINESS', true, null),
('bkc@mail.com', '12345', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'svg@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'skc@mail.com'));

-- insert into user_profile(simple_user_user_details_id)
-- select user_details_id from simple_usr where first_name in ('Vasya', 'Katya');

insert into car_identification(type_ident,name, user_car_id) values
('CarBrand', 'Audi', (select id from user_car where id=1)),
('CarBrand', 'BMW', (select id from user_car where id=2)),
('MotorType', 'mechanic', (select id from user_car where id=2)),
('MotorType', 'automatic', (select id from user_car where id=1)),
('TypeCar', 'coupe', (select id from user_car where id=1)),
('TypeCar', 'crossover', (select id from user_car where id=2));

insert into user_car(brand_id, motor_type_id, simple_user_id, type_car_id) values
((select id from car_identification where name like 'Audi' and type_ident like 'CarBrand'),
 (select id from car_identification where name like 'mechanic' and type_ident like 'MotorType'),
 (select user_details_id from simple_user where first_name in('Vasya')),
 (select id from car_identification where name like 'coupe' and type_ident like 'TypeCar')),

((select id from car_identification where name like 'BMW' and type_ident like 'CarBrand'),
 (select id from car_identification where name like 'automatic' and type_ident like 'MotorType'),
 (select user_details_id from simple_user where first_name in('Katya')),
 (select id from car_identification where name like 'crossover' and type_ident like 'TypeCar'));



