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

DROP TABLE IF EXISTS comment;

CREATE TABLE comment
(
    id      int PRIMARY KEY auto_increment,
    userId  int not null,
    message VARCHAR(1000)
);

INSERT INTO comment(userId, message)
VALUES (1, 'test massage1');

INSERT INTO comment(userId, message)
VALUES (1, 'test massage2');

INSERT INTO comment(userId, message)
VALUES (2, 'hello world');

INSERT INTO comment(userId, message)
VALUES (3, 'hi');