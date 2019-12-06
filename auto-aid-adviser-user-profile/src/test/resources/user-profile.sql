insert into adviser_usr(email, password, role, active, activation_code) values
('svg@mail.com', '12345', 'ROLE_USER', true, null),
('skc@mail.com', '12345', 'ROLE_USER', false, 'asdf-1234-simple'),
('bvg@mail.com', '12345', 'ROLE_BUSINESS', true, null),
('bkc@mail.com', '12345', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'svg@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'skc@mail.com'));

insert into car_identification(type_ident, name) values
('car_brand', 'Audi'),
('car_brand', 'BMW'),
('motor_type', 'mechanic'),
('motor_type', 'automatic'),
('type_car', 'coupe'),
('type_car', 'crossover');

insert into user_car(brand_id, motor_type_id, simple_user_user_details_id, car_type_id) values
(
 (select id from car_identification where name like 'Audi' and type_ident like 'car_brand'),
 (select id from car_identification where name like 'mechanic' and type_ident like 'motor_type'),
 (select user_details_id from simple_usr where first_name in('Vasya')),
 (select id from car_identification where name like 'coupe' and type_ident like 'type_car')
 ),

(
 (select id from car_identification where name like 'BMW' and type_ident like 'car_brand'),
 (select id from car_identification where name like 'automatic' and type_ident like 'motor_type'),
 (select user_details_id from simple_usr where first_name in('Katya')),
 (select id from car_identification where name like 'crossover' and type_ident like 'type_car')
 );


