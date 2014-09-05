# form_sample schema
 
# --- !Ups

CREATE SEQUENCE "prm_user_id_seq";
CREATE TABLE "prm_user" (
    "id" integer NOT NULL DEFAULT nextval('prm_user_id_seq'),
    "name" varchar(255) NOT NULL,
    "pass" varchar(255) NOT NULL
);

# --- !Downs
 
DROP TABLE "prm_user";
DROP SEQUENCE prm_user_id_seq;