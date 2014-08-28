# form_sample schema
 
# --- !Ups

CREATE SEQUENCE "customer_id_seq";
CREATE TABLE "customers" (
    "id" integer NOT NULL DEFAULT nextval('customer_id_seq'),
    "name" varchar(255)
);

# --- !Downs
 
DROP TABLE "customers";
DROP SEQUENCE customer_id_seq;