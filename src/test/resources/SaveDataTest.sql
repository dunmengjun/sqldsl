DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id   int PRIMARY KEY auto_increment,
    name VARCHAR(100),
    age  int,
    type int comment '1:学生, 2:工人, 3:无业'
);

INSERT INTO user(name, age, type)
VALUES ('bob', 17, 1);
