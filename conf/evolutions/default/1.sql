# --- !Ups

CREATE TABLE form_sample (
    id int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    form_str varchar(255)
);
 
# --- !Downs
 
DROP TABLE form_sample;