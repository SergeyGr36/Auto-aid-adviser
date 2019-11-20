insert into image(key_file_name, original_file_name, business_id) values
('1/1/asd-QWE-file1.jpg', 'file1.jpg', (select id from business limit 1)),
('1/1/asd-QWE-file2.jpg', 'file2.jpg', (select id from business limit 1)),
('1/1/asd-QWE-file3.jpg', 'file3.jpg', (select id from business limit 1));