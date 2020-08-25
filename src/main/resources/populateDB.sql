DELETE FROM USER_ROLES;
DELETE FROM MEALS;
DELETE FROM VOTES;
DELETE FROM RESTAURANTS;
DELETE FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
('USER', 100000),
('ADMIN', 100001),
('USER', 100001);

INSERT INTO RESTAURANTS (name) VALUES
('ASTORIA'),
('VICTORIA'),
('TIFFANY');

INSERT INTO MEALS (id_rest, DESCRIPTION, PRICE, DATE) VALUES
(100002, 'soup', 50, '2020-04-06'),
(100003, 'porridge', 25, '2020-04-06'),
(100003, 'juice', 10, '2020-04-06'),
(100004, 'coffee', 20, '2020-04-05');

INSERT INTO VOTES (user_id, id_rest, DATE) VALUES
(100000, 100002, '2020-04-06'),
(100000, 100002, '2020-04-05'),
(100001, 100003, '2020-04-06');

