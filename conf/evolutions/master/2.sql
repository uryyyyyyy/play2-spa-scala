# add first user (admin, admin)
# ALERT! it is unsafe because password is shown

# --- !Ups

INSERT INTO user VALUES (1, "admin", "d033e22ae348aeb5660fc2140aec35850c4da997");

# --- !Downs
 
DELETE FROM user where id = 1;