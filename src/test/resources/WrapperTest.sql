DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id   int PRIMARY KEY auto_increment,
    name VARCHAR(100),
    age  int,
    type int comment '1:学生, 2:工人, 3:无业'
);

INSERT INTO user(name, age, type)
VALUES ('alice', 16, 1);

INSERT INTO user(name, age, type)
VALUES ('bob', 17, 1);

INSERT INTO user(name, age, type)
VALUES ('tom', 17, 2);

INSERT INTO user(name, age, type)
VALUES ('dev', 16, 1);

INSERT INTO user(name, age, type)
VALUES ('test', 19, 3);

INSERT INTO user(name, age, type)
VALUES ('ba', 16, 3);