delete from simple_usr;
delete from adviser_usr;

insert into adviser_usr(email, password, role, active, activation_code) values
('test@gmail.com', 'testtest123', 'ROLE_USER', 'true', null);

insert into adviser_usr(email, password, role, active, activation_code) values
('business@gmail.com', 'Business123', 'ROLE_BUSINESS', 'true', null);
