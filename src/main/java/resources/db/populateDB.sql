DELETE FROM USER_ROLES;
DELETE FROM MENU;
DELETE FROM VOTES;
DELETE FROM RESTAURANTS;
DELETE FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO RESTAURANTS (name) VALUES
('ASTORIA'),
('VICTORIA'),
('TIFFANY');

INSERT INTO MENU (id_rest, MEAL, PRICE, DATE) VALUES
(100002, 'soup', 50.5, '2020-04-06'),
(100003, 'porridge', 25.5, '2020-04-06'),
(100003, 'juice', 10.0, '2020-04-06'),
(100004, 'coffee', 20.0, '2020-04-06');

INSERT INTO VOTES (user_id, id_rest, DATE) VALUES
(100000, 100002, '2020-04-06'),
(100001, 100003, '2020-04-06');

