CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP
);
CREATE TABLE IF NOT EXISTS city (
	id SERIAL PRIMARY KEY,
	name TEXT
);
INSERT INTO city(name) VALUES('Москва');
INSERT INTO city(name) VALUES('Казань');
CREATE TABLE IF NOT EXISTS candidate (
    id SERIAL PRIMARY KEY,
    name_vacancy TEXT,
    name TEXT,
    second_name TEXT,
    city_id INT REFERENCES city(id) ON DELETE CASCADE,
    created TIMESTAMP
);
CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	name TEXT,
	email TEXT,
	password TEXT
);
