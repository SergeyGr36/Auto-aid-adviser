insert into type_car(name) values ('coupe'), ('crossover');

insert into car_brand(name) values ('Audi'), ('BMW');

insert into car_model(name, car_brand_id, type_car_id) values
(
    'X5',
    (select id from car_brand where name like 'BMW'),
    (select id from type_car where name like 'crossover')
),
(
    'M5',
    (select id from car_brand where name like 'BMW'),
    (select id from type_car where name like 'crossover')
);

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


