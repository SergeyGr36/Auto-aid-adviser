insert into feedback(text, rating, business_id, user_id, create_date) values
(
    'Good service. Thanks!',
    5,
    (select id from business where name like 'user 1 STO 1'),
    (select id from adviser_usr where email like 'svg@mail.com'),
    now()
),
(
    'Хороший сервис!!!',
    5,
    (select id from business where name like 'user 1 STO 1'),
    (select id from adviser_usr where email like 'svg@mail.com'),
    now()
),
(
    'Не понравилось(((',
    2,
    (select id from business where name like 'user 1 STO 1'),
    (select id from adviser_usr where email like 'svg@mail.com'),
    now()
),
(
    'So so',
    3,
    (select id from business where name like 'user 1 STO 1'),
    (select id from adviser_usr where email like 'svg@mail.com'),
    now()
);