CREATE TABLE IF NOT EXISTS posts (
   id SERIAL PRIMARY KEY,
   name TEXT not null,
   city_id int,
   description TEXT not null,
   created timestamp not null,
   is_visible boolean
);

CREATE TABLE IF NOT EXISTS candidates (
    id SERIAL PRIMARY KEY,
    name TEXT not null,
    description TEXT not null,
    created timestamp not null,
    photo bytea
);

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name TEXT not null,
  email TEXT not null,
  password TEXT not null
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);