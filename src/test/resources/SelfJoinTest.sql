DROP TABLE IF EXISTS dept;

CREATE TABLE dept
(
    id     int PRIMARY KEY auto_increment,
    name   VARCHAR(100),
    parent int
);

INSERT INTO dept(name, parent)
VALUES ('集团总部', null);

INSERT INTO dept(name, parent)
VALUES ('人事部', 1);

INSERT INTO dept(name, parent)
VALUES ('开发部', 1);

INSERT INTO dept(name, parent)
VALUES ('财政部', 1);

INSERT INTO dept(name, parent)
VALUES ('采购部', 4);

INSERT INTO dept(name, parent)
VALUES ('会计部', 4);

INSERT INTO dept(name, parent)
VALUES ('业务开发组', 3);

INSERT INTO dept(name, parent)
VALUES ('研究院', 3);

INSERT INTO dept(name, parent)
VALUES ('日常办公采购组', 5);

INSERT INTO dept(name, parent)
VALUES ('大型物料采购组', 5);

INSERT INTO dept(name, parent)
VALUES ('特殊物件采购组', 5);