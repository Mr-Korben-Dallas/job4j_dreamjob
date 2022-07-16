CREATE TABLE posts (
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id int,
   description TEXT,
   created timestamp not null,
   is_visible boolean
);