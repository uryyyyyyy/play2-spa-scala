
# --- !Ups

CREATE TABLE users (
    id varchar(255) NOT NULL PRIMARY KEY,
    pass varchar(255) NOT NULL
    ) engine=InnoDB default charset utf8;

# --- !Downs
 
DROP TABLE users;