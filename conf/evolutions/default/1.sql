# form_sample schema
 
# --- !Ups

CREATE SEQUENCE form_sample_id_seq;
CREATE TABLE "form_sample" (
    "id" integer NOT NULL DEFAULT nextval('form_sample_id_seq'),
    "form_str" varchar(255)
);
 
# --- !Downs
 
DROP TABLE form_sample;
DROP SEQUENCE form_sample_id_seq;