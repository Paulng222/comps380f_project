CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) ,
    phone VARCHAR(50) ,
    address VARCHAR(150) ,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users(username,password) VALUES ('sam', '{noop}sampw');
INSERT INTO user_roles(username, role) VALUES ('sam', 'ROLE_STUDENT');
INSERT INTO user_roles(username, role) VALUES ('sam', 'ROLE_LECTURER');

INSERT INTO users(username,password) VALUES ('john', '{noop}johnpw');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_LECTURER');

INSERT INTO users(username,password) VALUES ('peter', '{noop}peterpw');
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

CREATE TABLE polls (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    question VARCHAR(50) NOT NULL, 
    option1 VARCHAR(50) NOT NULL,
    option2 VARCHAR(50) NOT NULL,
    option3 VARCHAR(50) NOT NULL,
    option4 VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE votes (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poll_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    voteOption INTEGER NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (poll_id) REFERENCES polls(id)
);

CREATE TABLE poll_comments (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poll_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    content VARCHAR(250),
    PRIMARY KEY (id),
    FOREIGN KEY (poll_id) REFERENCES polls(id)
);

INSERT INTO polls (question, option1, option2, option3, option4) VALUES ('how are you?','Good', 'fine', 'bad', 'IDK');

CREATE TABLE lecture_comments (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lectureComment_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    comment VARCHAR(250),
    creatTime VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
    
);