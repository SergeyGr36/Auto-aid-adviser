insert into adviser_usr(email, password, role, active, activation_code) values
('svg@mail.com', '12345', 'ROLE_USER', true, null),
('skc@mail.com', '12345', 'ROLE_USER', false, 'asdf-1234-simple'),
('bvg@mail.com', '12345', 'ROLE_BUSINESS', true, null),
('bkc@mail.com', '12345', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'svg@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'skc@mail.com'));

insert into type_car(name) values ('coupe'), ('crossover');

insert into car_brand(name) values ('Audi'), ('BMW');

insert into car_model(name, car_brand_id, type_car_id) values
    ('X5', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'crossover')),
    ('M5', (select id from car_brand where name like 'BMW'), (select id from type_car where name like 'crossover'))
;

insert into user_car(release_year, car_model_id, simple_user_user_details_id) values
(
     '2016',
     (select id from car_model where name like 'M5'),
     (select user_details_id from simple_usr where first_name in('Vasya'))
 ),

(
    '2016',
    (select id from car_model where name like 'X5'),
    (select user_details_id from simple_usr where first_name in('Vasya'))
);


