DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS People;

CREATE TABLE People
(
    id            INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name     VARCHAR(100) CHECK (LENGTH(full_name) >= 10 AND LENGTH(full_name) <= 100) UNIQUE NOT NULL,
    year_of_birth INT CHECK (year_of_birth >= 1900)                                                NOT NULL
);

CREATE TABLE Books
(
    id       INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    owner_id INT                      REFERENCES People (id) ON DELETE SET NULL,
    title    VARCHAR(100)             NOT NULL,
    author   VARCHAR(100)             NOT NULL,
    year     INT CHECK (year >= 1500) NOT NULL
);


INSERT INTO People(full_name, year_of_birth)
VALUES ('Ivanov Ivan Ivanovich', 1983),
       ('Petrov Petr Petrovich', 2001),
       ('Zhovner Ilya Olegovich', 1996),
       ('Ivanova Anna Ivanovna', 2004),
       ('Fedorov Miron Yanovich', 1987);

INSERT INTO Books(title, author, year)
VALUES ('Head First Java', 'Bert Bates and Kathy Sierra', 2003),
       ('The Catcher in the Rye', 'Jerome Salinger', 1951),
       ('War and Peace', 'Leo Tolstoy', 1867),
       ('Martin Eden', 'Jack London', 1909),
       ('Thinking in Java', 'Bruce Eckel', 1998),
       ('Effective Java', 'Joshua Bloch', 2001),
       ('Steve Jobs', 'Walter Isaacson', 2011);
