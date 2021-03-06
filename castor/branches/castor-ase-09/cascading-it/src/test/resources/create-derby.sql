CREATE SCHEMA TEST;

CREATE TABLE OneToOne_Author (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE OneToOne_Book (
    id INTEGER PRIMARY KEY,
    author_id INTEGER NOT NULL,
    time_stamp bigint,
    name varchar(100),
    FOREIGN KEY (author_id) REFERENCES OneToOne_Author (id)
);

CREATE TABLE OneToMany_Author (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE OneToMany_Book (
    id INTEGER PRIMARY KEY,
    author_id INTEGER NOT NULL,
    time_stamp bigint,
    name varchar(100),
    FOREIGN KEY (author_id) REFERENCES OneToMany_Author (id)
);

CREATE TABLE OneToMany_Bi_Author (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE OneToMany_Bi_Book (
    id INTEGER PRIMARY KEY,
    author_id INTEGER NOT NULL,
    time_stamp bigint,
    name varchar(100),
    FOREIGN KEY (author_id) REFERENCES OneToMany_Author (id)
);

CREATE TABLE ManyToOne_Author (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE ManyToOne_Book (
    id INTEGER PRIMARY KEY,
    author_id INTEGER NOT NULL,
    time_stamp bigint,
    name varchar(100),
    FOREIGN KEY (author_id) REFERENCES ManyToOne_Author (id)
);

CREATE TABLE ManyToMany_Author (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE ManyToMany_Book (
    id INTEGER PRIMARY KEY,
    time_stamp bigint,
    name varchar(100)
);

CREATE TABLE ManyToMany_BookAuthor (
    author_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    FOREIGN KEY (author_id) REFERENCES ManyToMany_Author (id),
    FOREIGN KEY (book_id) REFERENCES ManyToMany_Book (id)
);