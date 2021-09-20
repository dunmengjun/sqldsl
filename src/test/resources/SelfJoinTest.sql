DROP TABLE IF EXISTS dept;

CREATE TABLE dept
(
    id     int PRIMARY KEY auto_increment,
    name   VARCHAR(100),
    parent int
);

INSERT INTO dept(name, parent)
VALUES ('Group headquarters', null);

INSERT INTO dept(name, parent)
VALUES ('Personnel Department', 1);

INSERT INTO dept(name, parent)
VALUES ('Development Department', 1);

INSERT INTO dept(name, parent)
VALUES ('Ministry of Finance', 1);

INSERT INTO dept(name, parent)
VALUES ('Purchasing Department', 4);

INSERT INTO dept(name, parent)
VALUES ('Accounting Department', 4);

INSERT INTO dept(name, parent)
VALUES ('Business Development Team', 3);

INSERT INTO dept(name, parent)
VALUES ('Research Institute', 3);

INSERT INTO dept(name, parent)
VALUES ('Daily Office Purchasing Group', 5);

INSERT INTO dept(name, parent)
VALUES ('Large material purchasing group', 5);

INSERT INTO dept(name, parent)
VALUES ('Special Items Purchasing Group', 5);