insert into adviser_usr(email, password, role, active, activation_code) values
('svg@mail.com', '12345', 'ROLE_USER', true, null),
('skc@mail.com', '12345', 'ROLE_USER', false, 'asdf-1234-simple'),
('bvg@mail.com', '12345', 'ROLE_BUSINESS', true, null),
('bkc@mail.com', '12345', 'ROLE_BUSINESS', false, 'asdf-1234-business');

insert into simple_usr(first_name, last_name, user_details_id) values
('Vasya', 'Gogy', (select id from adviser_usr where email like 'svg@mail.com')),
('Katya', 'Cat', (select id from adviser_usr where email like 'skc@mail.com'));

-- insert into UserProfile(simple_usr_id, type_car, car_brand, motor_type ) values
-- ((select id from simple_usr where first_name like 'Vasya'), null, null, null ),
-- ((select id from simple_usr where first_name like 'Katya'), null, null, null );


insert into user_profile(simple_usr_id) values
(select user_details_id from simple_usr where first_name like 'Vasya'),
(select user_details_id from simple_usr where first_name like 'Katya');

insert into car(user_profile_id) values
(select id from user_profile where id = 1);

insert into car_identification(name) values
('Audi' where type_ident = 'CarBrand'),
('BMW' where type_ident = 'CarBrand'),
('Automatic' where type_ident = 'MotorType'),
('Mechanic' where type_ident = 'MotorType');