delete from usr;

insert into usr(first_name, last_name, mail, passwort, role, active, activation_code) values
('Van', 'Gog', 'vg@mail.com', '12345', 'ROLE_USER', 'true', null),
('Kin', 'Chi', 'kc@mail.com', '12345', 'ROLE_USER', 'false', 'asdf-1234-qwer');