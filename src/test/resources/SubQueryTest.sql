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

DROP TABLE IF EXISTS book;

CREATE TABLE book
(
    id         int PRIMARY KEY auto_increment,
    name       VARCHAR(100),
    createTime timestamp
);

INSERT INTO book(name, createTime)
VALUES ('book1', '2021-09-29 19:23:11');

INSERT INTO book(name, createTime)
VALUES ('book2', '2021-09-29 20:23:11');

INSERT INTO book(name, createTime)
VALUES ('book3', '2021-09-29 23:23:11');