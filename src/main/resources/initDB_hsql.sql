DROP TABLE user_roles IF EXISTS;
DROP INDEX menu_unique_restaurant_meal_date_idx IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP INDEX users_unique_email_idx IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOLEAN DEFAULT TRUE    NOT NULL
  );
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

CREATE TABLE user_roles
(
  user_id     INTEGER NOT NULL,
  role        VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  CONSTRAINT restaurants_idx UNIQUE (name)
);

CREATE TABLE menu
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  id_rest     INTEGER      NOT NULL,
  meal        VARCHAR(255) NOT NULL,
  price       INTEGER       NOT NULL,
  date        TIMESTAMP    NOT NULL
--   FOREIGN KEY (id_rest) REFERENCES restaurants (id) ON DELETE NO ACTION
);
CREATE UNIQUE INDEX menu_unique_restaurant_meal_date_idx
  ON menu (meal, date, id_rest);



  CREATE TABLE votes
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  user_id     INTEGER      NOT NULL,
  id_rest     INTEGER      NOT NULL,
  date        TIMESTAMP    NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
--   FOREIGN KEY (id_rest) REFERENCES restaurants (id)
  CONSTRAINT votes_idx UNIQUE (user_id, date)
);

