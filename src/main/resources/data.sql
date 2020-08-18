DELETE FROM books;
ALTER SEQUENCE global_seq
RESTART WITH 100000;

INSERT INTO books (title, author, description)
VALUES ('The Dark Tower', 'Stephen King', 'fiction'),
       ('Fahrenheit 450', 'Ray Bradbury', 'fiction'),
       ('Sherlock Holmes', 'Arthur Conan Doyle', 'detective'),
       ('Ride The Bullet', 'Stephen King', 'fiction'),
       ('Dandelion Wine', 'Ray Bradbury', 'romance');