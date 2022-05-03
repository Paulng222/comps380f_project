CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users VALUES ('sam', '{noop}sampw');
INSERT INTO user_roles(username, role) VALUES ('sam', 'ROLE_STUDENT');
INSERT INTO user_roles(username, role) VALUES ('sam', 'ROLE_LECTURER');

INSERT INTO users VALUES ('john', '{noop}johnpw');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_LECTURER');

INSERT INTO users VALUES ('peter', '{noop}peterpw');
INSERT INTO user_roles(username, role) VALUES ('peter', 'ROLE_STUDENT');




CREATE TABLE lecture (
 id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 name VARCHAR(255) NOT NULL,
 subject VARCHAR(255) NOT NULL,
 body VARCHAR(255) NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE notes (
 id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 filename VARCHAR(255) DEFAULT NULL,
 content_type VARCHAR(255) DEFAULT NULL,
 content BLOB DEFAULT NULL,
 lecture_id INTEGER DEFAULT NULL,
 PRIMARY KEY (id),
 FOREIGN KEY (lecture_id) REFERENCES lecture(id)
);

