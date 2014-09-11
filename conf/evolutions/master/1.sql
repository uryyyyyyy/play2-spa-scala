
# --- !Ups

CREATE TABLE user (
    id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username varchar(255) NOT NULL,
    pass varchar(255) NOT NULL
    ) engine=InnoDB default charset utf8;

# --- !Downs
 
DROP TABLE user;