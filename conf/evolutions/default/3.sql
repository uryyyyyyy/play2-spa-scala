
# --- !Ups

CREATE TABLE IF NOT EXISTS user (
    id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) NOT NULL,
    pass varchar(255) NOT NULL
    );

# --- !Downs
 
DROP TABLE user;