# --- !Ups

CREATE TABLE customers (
    id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL
) engine=InnoDB default charset utf8;

# --- !Downs
 
DROP TABLE customers;