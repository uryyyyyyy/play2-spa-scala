# add first users (admin, admin)
# ALERT! it is unsafe because password is shown

# --- !Ups

INSERT INTO users VALUES ("admin", "d033e22ae348aeb5660fc2140aec35850c4da997");

# --- !Downs
 
DELETE FROM users where id = 1;