insert into feedback(text, rating, business_id, user_id, create_date) values
(
    'Good service. Thanks!',
    5,
    (select id from business where name like 'user 1 STO 1'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'Хороший сервис!!!',
    5,
    (select id from business where name like 'user 1 STO 1'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'Не понравилось(((',
    2,
    (select id from business where name like 'user 1 STO 1'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
),
(
    'So so',
    3,
    (select id from business where name like 'user 1 STO 1'),
    (select user_details_id from simple_usr where first_name like 'Vasya'),
    now()
);