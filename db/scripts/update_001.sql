CREATE TABLE IF NOT EXISTS posts (
   id SERIAL PRIMARY KEY,
   name TEXT not null,
   city_id int not null,
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