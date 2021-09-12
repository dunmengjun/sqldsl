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

INSERT INTO user(name, age)
VALUES ('weak', 17);

DROP TABLE IF EXISTS comment;

CREATE TABLE comment
(
    id      int PRIMARY KEY auto_increment,
    userId  int           not null,
    message VARCHAR(1000),
    status  int default 0 not null
);

INSERT INTO comment(userId, message, status)
VALUES (1, 'test massage1', 1);

INSERT INTO comment(userId, message, status)
VALUES (1, 'test massage2', 1);

INSERT INTO comment(userId, message)
VALUES (2, 'hello world');

INSERT INTO comment(userId, message)
VALUES (3, 'hi');

INSERT INTO comment(userId, message, status)
VALUES (5, 'hello', 1);

DROP TABLE IF EXISTS satisfaction;

CREATE TABLE satisfaction
(
    id        int PRIMARY KEY auto_increment,
    userId    int not null,
    commentId int not null,
    rating    int not null default 5
);

INSERT INTO satisfaction(userId, commentId, rating)
VALUES (1, 1, 1);

INSERT INTO satisfaction(userId, commentId, rating)
VALUES (2, 1, 3);

INSERT INTO satisfaction(userId, commentId, rating)
VALUES (3, 2, 4);