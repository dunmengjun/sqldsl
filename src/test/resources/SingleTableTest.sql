DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id   int PRIMARY KEY auto_increment,
    name VARCHAR(100),
    age  int
);

INSERT INTO user(name, age)
VALUES ('alice', 16);

INSERT INTO user(name, age)
VALUES ('bob', 17);

INSERT INTO user(name, age)
VALUES ('tom', 17);