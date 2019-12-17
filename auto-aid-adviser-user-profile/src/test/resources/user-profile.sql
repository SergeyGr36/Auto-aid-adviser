insert into adviser_usr(email, password, role, active, activation_code) values
('svg@mail.com', '12345', 'ROLE_USER', true, null),
('skc@mail.com', '12345', 'ROLE_USER', false, 'asdf-1234-simple'),
('bvg@mail.com', '12345', 'ROLE_BUSINESS', true, null),
('bkc@mail.com', '12345', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'svg@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'skc@mail.com'));

insert into car_identification(type_ident, name, parent_id) values
('type_car', 'coupe', null),
('type_car', 'crossover', null),
('car_brand', 'Audi', (select id from car_identification where name like 'coupe')),
('car_brand', 'BMW', (select id from car_identification where name like 'coupe')),
('car_brand', 'BMW', (select id from car_identification where name like 'crossover')),
('car_model', 'X5', (select id from car_identification where name like 'BMW' and like )),
('car_model', 'M5', (select id from car_identification where name like 'BMW'));

insert into user_car(release_year, car_model_id, simple_user_user_details_id) values
(
     '2016',
     (select id from car_identification where name like 'M5'),
     (select user_details_id from simple_usr where first_name in('Vasya'))
 ),

(
    '2016',
    (select id from car_identification where name like 'X5'),
    (select user_details_id from simple_usr where first_name in('Vasya'))
);


